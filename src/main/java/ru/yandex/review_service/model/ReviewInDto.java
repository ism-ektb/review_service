package ru.yandex.review_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "Новый отзыв")
@Getter
@Builder
public class ReviewInDto {

    @Schema(name = "имя автора отзыва")
    @NotBlank(message = "Имя автора не должно быть пустым")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;
    @NotBlank(message = "Название отзыва не должно быть пустым")
    @Schema(name = "Название отзыва отзыва")
    @Size(min = 3, max = 50, message = "Название отзыва пользователя должно содержать от 3 до 50 символов")
    private String title;
    @NotBlank(message = "Текст отзыва не должен быть пустым")
    @Schema(name = "Текст отзыва")
    @Size(min = 5, max = 200, message = "Текст отзыва должен содержать от 5 до 200 символов")
    private String content;
    @NotNull(message = "Id мероприятия не должно быть пустым")
    @Schema(name = "Id мероприятия на который дается отзыв")
    private Long eventId;
}
