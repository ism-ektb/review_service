package ru.yandex.review_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.review_service.model.Dislike;


public interface DislikeRepository extends JpaRepository<Dislike, Long> {
    Dislike findByReviewId(long reviewId);

    boolean existsByUserIdAndReviewId(Long userId, long reviewId);

    void deleteByReviewId(long reviewId);
}
