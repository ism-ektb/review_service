package ru.yandex.review_service.mapper;

import org.mapstruct.Mapper;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewOutDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface ReviewListMapper {
    List<ReviewOutDto> modelsToDtos(List<Review> dtos);
}
