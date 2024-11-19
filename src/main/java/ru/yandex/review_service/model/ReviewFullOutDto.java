package ru.yandex.review_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
@Jacksonized
@Schema(name = "Полная информация об отзыве")
public class ReviewFullOutDto {
    @Schema(name = "Id отзыва")
    private Long id;
    @Schema(name = "Id автора отзыва")
    private Long authorId;
    @Schema(name = "Имя автора отзыва")
    private String username;
    @Schema(name = "Название отзыва")
    private String title;
    @Schema(name = "Текст отзыва")
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "Двта сознания отзыва", example = "2022-06-16 16:37:23")
    private LocalDateTime createdDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "Двта измениения отзыва", example = "2022-06-16 16:37:23")
    private LocalDateTime updatedDateTime;
    @Schema(name = "Оценка отзыва")
    private Long mark;
    @Schema(name = "id мероприятия на которое дается отзыв")
    private Long eventId;
}