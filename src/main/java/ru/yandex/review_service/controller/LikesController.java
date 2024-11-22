package ru.yandex.review_service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.review_service.service.LikesService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Validated
@Tag(name = "Добавление лайков/диздайков к отзыву")
@Slf4j
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/like/{reviewId}")
    public void like(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.like(userId, reviewId);
    }

    @DeleteMapping("/like/{reviewId}")
    public void deleteLike(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.deleteLike(userId,reviewId);
    }

    @PostMapping("/dislike/{reviewId}")
    public void dislike(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.dislike(userId,reviewId);
    }

    @DeleteMapping("/dislike/{reviewId}")
    public void deleteDislike(@Valid @RequestHeader long userId, @PathVariable @Positive long reviewId) throws Exception {
        likesService.deleteDislike(userId,reviewId);
    }
}

