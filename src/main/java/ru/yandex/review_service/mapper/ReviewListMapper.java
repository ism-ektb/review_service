package ru.yandex.review_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewOutDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface ReviewListMapper {
    @Mapping(target = "likes", expression = "java(review.getLikes().size())") // Преобразование для количества лайков
    @Mapping(target = "dislikes", expression = "java(review.getDislikes().size())") // Преобразование для количества дизлайков
    List<ReviewOutDto> modelsToDtos(List<Review> dtos);
}
