package ru.yandex.review_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.yandex.review_service.exception.exception.BaseRelationshipException;
import ru.yandex.review_service.exception.exception.NoFoundObjectException;
import ru.yandex.review_service.mapper.ReviewListMapper;
import ru.yandex.review_service.mapper.ReviewMapper;
import ru.yandex.review_service.mapper.ReviewPatcher;
import ru.yandex.review_service.model.*;
import ru.yandex.review_service.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository repository;
    private final ReviewMapper mapper;
    private final ReviewListMapper listMapper;
    private final ReviewPatcher patcher;
    /**
     * Создание отзыва
     *
     * @param userId
     * @param reviewInDto
     */
    @Override
    public ReviewFullOutDto create(Long userId, ReviewInDto reviewInDto) {
        Review review = mapper.dtoToModel(reviewInDto);
        review.setAuthorId(userId);
        return mapper.modelToFullDto(repository.save(review));
    }

    /**
     * Редактирование отзыва овтором
     *
     * @param userId
     * @param reviewPatchDto
     */
    @Override
    public ReviewFullOutDto patch(Long userId, Long reviewId, ReviewPatchDto reviewPatchDto) {
        Review review = repository.findById(reviewId).orElseThrow(()  -> new NoFoundObjectException(
                String.format("Отзыв с id=%s не найден", reviewId)));
        if (review.getAuthorId() != userId) throw new BaseRelationshipException(
                String.format("Изменить отзыв с Id= %s пользователь с Id= %s не может", reviewId, userId)
        );
        review = patcher.patch(review, reviewPatchDto);
        return mapper.modelToFullDto(repository.save(review));
    }

    /**
     * Получить отзыв по Id
     *
     * @param reviewId
     */
    @Override
    public ReviewOutDto getReview(Long reviewId) {
        Review review = repository.findById(reviewId).orElseThrow(()  -> new NoFoundObjectException(
                String.format("Отзыв с id=%s не найден", reviewId)));
        return mapper.modelToDto(review);
    }

    /**
     * Получить отзывы по Id события
     *
     * @param eventId
     * @param pageRequest
     */
    @Override
    public List<ReviewOutDto> findReviewByEventId(Long eventId, PageRequest pageRequest) {

        return listMapper.modelsToDtos(repository.findAllByEventId(eventId, pageRequest));
    }

    /**
     * Удалить отзыв по Id автором
     *
     * @param userId
     * @param reviewId
     */
    @Override
    public void deleteReview(Long userId, Long reviewId) {

    }
}
