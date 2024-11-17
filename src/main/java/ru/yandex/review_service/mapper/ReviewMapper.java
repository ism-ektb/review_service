package ru.yandex.review_service.mapper;

import org.mapstruct.Mapper;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review dtoToModel(ReviewInDto reviewInDto);
    ReviewFullOutDto modelToFullDto(Review review);
    ReviewOutDto modelToDto(Review review);
}
