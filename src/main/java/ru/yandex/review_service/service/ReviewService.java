package ru.yandex.review_service.service;

import org.springframework.data.domain.PageRequest;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;
import ru.yandex.review_service.model.ReviewPatchDto;

import java.util.List;

public interface ReviewService {
    /**
     * Создание отзыва
     */
    ReviewFullOutDto create(Long userId, ReviewInDto reviewInDto);
    /**
     * Редактирование отзыва овтором
     */
    ReviewFullOutDto patch(Long userId, Long reviewId, ReviewPatchDto reviewPatchDto);
    /**
     * Получить отзыв  по Id
     */
    ReviewOutDto getReview(Long reviewId);
    /**
     * Получить отзывы по Id события
     */
    List<ReviewOutDto> findReviewByEventId(Long eventId, PageRequest pageRequest);
    /**
     * Удалить отзыв по Id автором
     */
    void deleteReview(Long userId, Long reviewId);
}
