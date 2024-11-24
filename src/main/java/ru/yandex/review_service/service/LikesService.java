package ru.yandex.review_service.service;

import org.springframework.stereotype.Service;


public interface LikesService {
    void like(long userId, long reviewId);

    void deleteLike(long userId, long reviewId);

    void dislike(long userId, long reviewId);

    void deleteDislike(long userId, long reviewId);
}
