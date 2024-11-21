package ru.yandex.review_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.review_service.model.Like;

public interface LikesRepository extends JpaRepository<Like, Long> {
}
