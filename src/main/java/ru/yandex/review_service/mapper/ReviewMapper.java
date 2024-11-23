package ru.yandex.review_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review dtoToModel(ReviewInDto reviewInDto);
    @Mapping(target = "likes", expression = "java(review.getLikes().size())") // Преобразование для количества лайков
    @Mapping(target = "dislikes", expression = "java(review.getDislikes().size())") // Преобразование для количества дизлайков
    ReviewFullOutDto modelToFullDto(Review review);
    @Mapping(target = "likes", expression = "java(review.getLikes().size())") // Преобразование для количества лайков
    @Mapping(target = "dislikes", expression = "java(review.getDislikes().size())") // Преобразование для количества дизлайков
    ReviewOutDto modelToDto(Review review);
}
