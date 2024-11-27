package ru.yandex.review_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.review_service.exception.exception.BaseRelationshipException;
import ru.yandex.review_service.exception.exception.NoFoundObjectException;
import ru.yandex.review_service.model.Dislike;
import ru.yandex.review_service.model.Like;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.repository.DislikeRepository;
import ru.yandex.review_service.repository.LikesRepository;
import ru.yandex.review_service.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService{

    private final LikesRepository likesRepository;
    private final DislikeRepository dislikeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void like(long userId, long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoFoundObjectException("Такой отзыв не существует"));
        if(review.getAuthorId().equals(userId)){
            throw new BaseRelationshipException("Нельзя оценить свой отзыв");
        }
        if(likesRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            throw new BaseRelationshipException("Нельзя оценить отзыв дважды");
        } else if(dislikeRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            deleteDislike(userId, reviewId);
        } else {
            Like like = Like.builder()
                    .review(review)
                    .userId(userId)
                    .build();
            like = likesRepository.save(like);
            review.getLikes().add(like);
            reviewRepository.save(review);
        }
    }

    @Override
    public void deleteLike(long userId, long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoFoundObjectException("Такой отзыв не существует"));
        Like like = likesRepository.findByReviewId(reviewId);
        review.getLikes().remove(like);
        reviewRepository.save(review);
        likesRepository.deleteByReviewId(reviewId);
    }

    @Override
    public void dislike(long userId, long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoFoundObjectException("Такой отзыв не существует"));
        if(review.getAuthorId().equals(userId)){
            throw new BaseRelationshipException("Нельзя оценить свой отзыв");
        }
        if(dislikeRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            throw new BaseRelationshipException("Нельзя оценить свой дважды");
        } else if(likesRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            deleteLike(userId, reviewId);
        } else {
            Dislike dislike = Dislike.builder()
                    .review(review)
                    .userId(userId)
                    .build();
            dislike = dislikeRepository.save(dislike);
            review.getDislikes().add(dislike);
            reviewRepository.save(review);
        }
    }

    @Override
    public void deleteDislike(long userId, long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoFoundObjectException("Такой отзыв не существует"));
        Dislike dislike = dislikeRepository.findByReviewId(reviewId);
        review.getDislikes().remove(dislike);
        reviewRepository.save(review);
        likesRepository.deleteByReviewId(reviewId);
    }
}
