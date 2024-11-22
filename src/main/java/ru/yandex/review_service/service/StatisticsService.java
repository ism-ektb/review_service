package ru.yandex.review_service.service;

import ru.yandex.review_service.dto.StatisticsResponse;
import ru.yandex.review_service.dto.TopReviewResponse;

public interface StatisticsService {
    StatisticsResponse getStatisticsEvent(final Long eventId);

    StatisticsResponse getStatisticsAuthor(final Long authorId);

    TopReviewResponse getTopOfBestAndWorstEvent(final Long eventId);
}
