package ru.yandex.review_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.yandex.review_service.dto.StatisticsResponse;
import ru.yandex.review_service.dto.TopReviewResponse;
import ru.yandex.review_service.exception.exception.BaseRelationshipException;
import ru.yandex.review_service.exception.exception.NoFoundObjectException;
import ru.yandex.review_service.mapper.ReviewListMapper;
import ru.yandex.review_service.mapper.ReviewMapper;
import ru.yandex.review_service.mapper.ReviewPatcher;
import ru.yandex.review_service.model.*;
import ru.yandex.review_service.repository.ReviewRepository;


import java.util.HashSet;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService, StatisticsService {

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
        review.setLikes(new HashSet<>());
        review.setDislikes(new HashSet<>());
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
        Review review = repository.findById(reviewId).orElseThrow(()  -> new NoFoundObjectException(
                String.format("Отзыв с id=%s не найден", reviewId)));
        if (!Objects.equals(review.getAuthorId(), userId)) {
            throw new BaseRelationshipException(
                    String.format("Пользователь с id = %s не может удалить отзыв с id= %s", userId, reviewId));
        }
        repository.deleteById(reviewId);
    }

    @Override
    public StatisticsResponse getStatisticsEvent(final Long eventId) {
        return getStatistics(repository.findAllByEventId(eventId));

    }

    @Override
    public StatisticsResponse getStatisticsAuthor(final Long authorId) {
        return getStatistics(repository.findAllByAuthorId(authorId));
    }

    @Override
    public TopReviewResponse getTopOfBestAndWorstEvent(final Long eventId) {
        return getTopOfBestAndWorst(repository.findAllByEventId(eventId));
    }

    private StatisticsResponse getStatistics(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return StatisticsResponse.builder()
                    .count(0)
                    .avg(0D)
                    .like("0%")
                    .dislike("0%")
                    .build();
        }
        int count = reviews.size();
        double avg = 0;
        double like = 0;
        double dislike = 0;
        for (Review review : reviews) {
            if (review.getMark() == 0) {
                dislike++;
            } else if (review.getMark() == 1) {
                like++;
            }
        }

        if (like != 0 || dislike != 0) {
            like = 100 / (like + dislike) * like;
            dislike = 100 / (like + dislike) * dislike;
        }
        if (like + dislike > 10 && dislike > like) {
            avg = like / 2;
        } else {
            avg = (like + dislike) / 2;
        }
        return StatisticsResponse.builder()
                .count(count)
                .avg(avg)
                .like(like + "%")
                .dislike(dislike + "%")
                .build();
    }

    private TopReviewResponse getTopOfBestAndWorst(List<Review> reviews) {
        List<ReviewOutDto> topOfTheBest = new ArrayList<>();
        List<ReviewOutDto> topOfTheWorst = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getMark() == 0 && topOfTheWorst.size() < 3) {
                topOfTheWorst.add(mapper.modelToDto(review));
            } else if (review.getMark() == 1 && topOfTheBest.size() < 3) {
                topOfTheBest.add(mapper.modelToDto(review));
            }
        }
        return TopReviewResponse.builder()
                .topOfTheBest(topOfTheBest)
                .topOfTheWorst(topOfTheWorst)
                .build();
    }
}
