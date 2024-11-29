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
import ru.yandex.review_service.dto.StatisticsResponse;
import ru.yandex.review_service.dto.TopReviewResponse;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;
import ru.yandex.review_service.model.ReviewPatchDto;
import ru.yandex.review_service.service.LikesService;
import ru.yandex.review_service.service.ReviewService;
import ru.yandex.review_service.service.StatisticsService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Validated
@Tag(name = "Работа с отзывами о событиях")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;
    private final StatisticsService statisticsService;

    private final LikesService likesService;

    @PostMapping
    @Operation(description = "Создание нового отзыва")
    @ResponseStatus(HttpStatus.CREATED)
    ReviewFullOutDto createUser(@RequestHeader long userId,
                                @RequestBody @Valid ReviewInDto reviewInDto) {
        ReviewFullOutDto reviewFullOutDto = service.create(userId, reviewInDto);
        log.info("Создан отзыв с Id= {}", reviewFullOutDto.getId());
        return reviewFullOutDto;
    }

    @PatchMapping("/{reviewId}")
    @Operation(description = "Изменение отзыва о событии")
    ReviewFullOutDto patchReview(@PathVariable @Positive long reviewId,
                                 @RequestHeader @Positive long userId,
                                 @RequestBody @Valid ReviewPatchDto reviewPatchDto) {
        ReviewFullOutDto reviewFullOutDto = service.patch(userId, reviewId, reviewPatchDto);
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
                                  @RequestParam @Positive long eventId) {
        List<ReviewOutDto> list = service.findReviewByEventId(eventId,
                PageRequest.of(from / size, size));
        log.info("Получен список отзывов о событии с id= {}", eventId);
        return list;
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление отзыва")
    void deleteReview(@RequestHeader @Positive long userId,
                      @PathVariable @Positive long id) {
        service.deleteReview(userId, id);
        log.info("отзыв с id = {} удален", id);
    }

    @PostMapping("/like/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void like(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.like(userId, reviewId);
    }

    @DeleteMapping("/like/{reviewId}")
    public void deleteLike(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.deleteLike(userId, reviewId);
    }

    @PostMapping("/dislike/{reviewId}")
    public void dislike(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.dislike(userId, reviewId);
    }



    @DeleteMapping("/dislike/{reviewId}")
    public void deleteDislike(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.deleteDislike(userId, reviewId);
    }

    @GetMapping("/statistics/event/{id}")
    StatisticsResponse getStatisticsEvent(@PathVariable Long id) {
        return statisticsService.getStatisticsEvent(id);
    }

    @GetMapping("/statistics/author/{id}")
    StatisticsResponse getStatisticsAuthor(@PathVariable Long id) {
        return statisticsService.getStatisticsAuthor(id);
    }

    @GetMapping("/statistics/general/event/{id}")
    TopReviewResponse getTopOfBestAndWorstEvent(@PathVariable Long id) {
        return statisticsService.getTopOfBestAndWorstEvent(id);

    }


}
