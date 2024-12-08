package ru.yandex.review_service.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Microsoft)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review dtoToModel(ReviewInDto reviewInDto) {
        if ( reviewInDto == null ) {
            return null;
        }

        Review.ReviewBuilder<?, ?> review = Review.builder();

        review.username( reviewInDto.getUsername() );
        review.title( reviewInDto.getTitle() );
        review.content( reviewInDto.getContent() );
        review.eventId( reviewInDto.getEventId() );

        return review.build();
    }

    @Override
    public ReviewFullOutDto modelToFullDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewFullOutDto.ReviewFullOutDtoBuilder reviewFullOutDto = ReviewFullOutDto.builder();

        reviewFullOutDto.id( review.getId() );
        reviewFullOutDto.authorId( review.getAuthorId() );
        reviewFullOutDto.username( review.getUsername() );
        reviewFullOutDto.title( review.getTitle() );
        reviewFullOutDto.content( review.getContent() );
        reviewFullOutDto.createdDateTime( review.getCreatedDateTime() );
        reviewFullOutDto.updatedDateTime( review.getUpdatedDateTime() );
        reviewFullOutDto.mark( review.getMark() );
        reviewFullOutDto.eventId( review.getEventId() );

        reviewFullOutDto.likes( review.getLikes() != null ? review.getLikes().size() : 0 );
        reviewFullOutDto.dislikes( review.getDislikes() != null ? review.getDislikes().size() : 0 );

        return reviewFullOutDto.build();
    }

    @Override
    public ReviewOutDto modelToDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewOutDto.ReviewOutDtoBuilder reviewOutDto = ReviewOutDto.builder();

        reviewOutDto.id( review.getId() );
        reviewOutDto.username( review.getUsername() );
        reviewOutDto.title( review.getTitle() );
        reviewOutDto.content( review.getContent() );
        reviewOutDto.createdDateTime( review.getCreatedDateTime() );
        reviewOutDto.updatedDateTime( review.getUpdatedDateTime() );
        reviewOutDto.mark( review.getMark() );
        reviewOutDto.eventId( review.getEventId() );

        reviewOutDto.likes( review.getLikes() != null ? review.getLikes().size() : 0 );
        reviewOutDto.dislikes( review.getDislikes() != null ? review.getDislikes().size() : 0 );

        return reviewOutDto.build();
    }
}
