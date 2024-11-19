package ru.yandex.review_service.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.review_service.exception.exception.BaseRelationshipException;
import ru.yandex.review_service.exception.exception.NoFoundObjectException;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;
import ru.yandex.review_service.model.ReviewPatchDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15");
    @Autowired
    private ReviewService service;

    @Test
    @Order(1)
    void create_goodResult() {
        ReviewInDto reviewInDto = ReviewInDto.builder().eventId(1L)
                .title("review").content("review Text").username("Ivan")
                .build();
        ReviewFullOutDto reviewFullOutDto = service.create(1L, reviewInDto);
        assertEquals(reviewFullOutDto.getId(), 2L);
        assertEquals(reviewFullOutDto.getAuthorId(), 1L);

    }

    @Test
    @Order(2)
    void patch_userIdIsBad() {

        ReviewPatchDto reviewPatchDto = ReviewPatchDto.builder().title("new title").build();
        assertThrows(BaseRelationshipException.class, () -> service.patch(10L, 2L, reviewPatchDto));
    }

    @Test
    @Order(3)
    void patch_goodResult() {

        ReviewPatchDto reviewPatchDto = ReviewPatchDto.builder().title("new title").build();
        ReviewFullOutDto reviewFullOutDto = service.patch(1L, 2L, reviewPatchDto);
        assertEquals(reviewFullOutDto.getTitle(), "new title");
    }

    @Test
    @Order(4)
    void findReviewByEventId() {
        List<ReviewOutDto> list = service.findReviewByEventId(1L, PageRequest.of(0, 1));
        assertEquals(list.size(), 1);
    }

    @Test
    void deleteReview() {
        assertThrows(BaseRelationshipException.class, () -> service.deleteReview(10L, 2L));
        assertThrows(NoFoundObjectException.class, () -> service.deleteReview(11L, 20L));
        service.deleteReview(1L, 2L);

    }
}