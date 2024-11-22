package ru.yandex.review_service.mapper;

import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewOutDto;

public class LikesMapper {

    public static ReviewOutDto toReviewOutDto(Review review){
        return ReviewOutDto.builder()
                .id(review.getId())
                .username(review.getUsername())
                .title(review.getTitle())
                .content(review.getContent())
                .createdDateTime(review.getCreatedDateTime())
                .updatedDateTime(review.getUpdatedDateTime())
                .mark(review.getMark())
                .eventId(review.getEventId())
                .likes(review.getLikes().size())
                .dislikes(review.getDislikes().size())
                .build();
    }

    public static ReviewFullOutDto toReviewFullOutDto(Review review){
        return ReviewFullOutDto.builder()
                .id(review.getId())
                .authorId(review.getAuthorId())
                .username(review.getUsername())
                .title(review.getTitle())
                .content(review.getContent())
                .createdDateTime(review.getCreatedDateTime())
                .updatedDateTime(review.getUpdatedDateTime())
                .mark(review.getMark())
                .eventId(review.getEventId())
                .likes(review.getLikes().size())
                .dislikes(review.getDislikes().size())
                .build();
    }
}
