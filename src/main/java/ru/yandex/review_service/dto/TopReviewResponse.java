package ru.yandex.review_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.review_service.model.ReviewOutDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopReviewResponse {
    private List<ReviewOutDto> topOfTheBest;
    private List<ReviewOutDto> topOfTheWorst;
}
