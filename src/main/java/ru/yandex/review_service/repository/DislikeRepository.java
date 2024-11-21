package ru.yandex.review_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.review_service.model.Dislike;


public interface DislikeRepository extends JpaRepository<Dislike, Long> {
}
