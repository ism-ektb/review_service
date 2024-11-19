package ru.yandex.review_service.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewPatchDto;

import java.time.LocalDateTime;

@Component
public class ReviewPatcher {
    public Review patch(Review review, ReviewPatchDto dto){
        Review newReview = Review.builder()
                .id(review.getId())
                .authorId(review.getAuthorId())
                .username(dto.getUsername() != null ? dto.getUsername() : review.getUsername())
                .title(dto.getTitle() != null ? dto.getTitle() : review.getTitle())
                .content(dto.getContent() != null ? dto.getContent() : review.getContent())
                .createdDateTime(review.getCreatedDateTime())
                .updatedDateTime(LocalDateTime.now())
                .mark(review.getMark())
                .eventId(review.getEventId()).build();
        return newReview;
    }
}
