package ru.yandex.review_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "Запрос на изменение отзыва о событии")
public class ReviewPatchDto {
    @Schema(name = "имя автора отзыва")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;
    @Schema(name = "Название отзыва отзыва")
    private String title;
    @Schema(name = "Текст отзыва")
    @Size(min = 5, max = 200, message = "Текст отзыва должен содержать от 5 до 200 символов")
    private String content;

}
