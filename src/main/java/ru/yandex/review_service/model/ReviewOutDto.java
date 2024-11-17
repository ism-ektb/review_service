package ru.yandex.review_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
@Schema(name = "Краткий текст отзыва")
public class ReviewOutDto {
    @Schema(name = "Id отзыва")
    private Long id;
    @Schema(name = "Имя автора отзыва")
    private String username;
    @Schema(name = "Название отзыва")
    private String title;
    @Schema(name = "Текст отзыва")
    private String content;
    @Schema(name = "Двта сознания отзыва", example = "2022-06-16 16:37:23")
    private LocalDateTime createdDateTime;
    @Schema(name = "Двта измениения отзыва", example = "2022-06-16 16:37:23")
    private LocalDateTime updatedDateTime;
    @Schema(name = "Оценка отзыва")
    private Long mark;
    @Schema(name = "id мероприятия на которое дается отзыв")
    private Long eventId;
}
