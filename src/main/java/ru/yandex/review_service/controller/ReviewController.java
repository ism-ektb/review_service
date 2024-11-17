package ru.yandex.review_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;
import ru.yandex.review_service.model.ReviewPatchDto;
import ru.yandex.review_service.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с отзывами о событиях")
@Slf4j
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    @Operation(description = "Создание нового отзыва")
    @ResponseStatus(HttpStatus.CREATED)
    ReviewFullOutDto createUser(@RequestHeader long userId,
                                @RequestBody @Valid ReviewInDto reviewInDto) {
        ReviewFullOutDto reviewFullOutDto = service.create(userId, reviewInDto);
        log.info("Создан отзыв с Id= {}", reviewFullOutDto.getId());
        return reviewFullOutDto;
    }

    @PatchMapping
    @Operation(description = "Изменение отзыва о событии")
    ReviewFullOutDto patchReview(@RequestHeader @Positive long userId,
                                 @RequestBody @Valid ReviewPatchDto reviewPatchDto) {
        ReviewFullOutDto reviewFullOutDto = service.patch(userId, reviewPatchDto);
        log.info("Данные по отзыву с Id {} обновлены", reviewFullOutDto.getId());
        return reviewFullOutDto;
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение данных о отзыве")
    ReviewOutDto getUser(@PathVariable long id) {
        ReviewOutDto reviewOutDto = service.getReview(id);
        log.info("Получены данные о пользователе с id {}", reviewOutDto.getId());
        return reviewOutDto;
    }

    @GetMapping
    @Operation(description = "Получение списка всех отзывов о событии")
    List<ReviewOutDto> getAllUser(@RequestParam(defaultValue = "0", required = false) int from,
                                  @RequestParam(defaultValue = "10", required = false) int size,
                                  @PathVariable @Positive long id) {
        List<ReviewOutDto> list = service.findReviewByEventId(id,
                PageRequest.of(from / size, size));
        log.info("Получен список отзывов о событии с id= {}", id);
        return list;
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление отзыва")
    void deleteReview(@RequestHeader @Positive long userId,
                    @PathVariable @Positive long id) {
        service.deleteReview(userId, id);
        log.info("отзыв с id = {} удален", id);
    }
}
