package ru.yandex.review_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.bouncycastle.pqc.jcajce.provider.Dilithium;
import ru.yandex.review_service.exception.exception.BaseRelationshipException;
import ru.yandex.review_service.model.Dislike;
import ru.yandex.review_service.model.Like;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.repository.DislikeRepository;
import ru.yandex.review_service.repository.LikesRepository;
import ru.yandex.review_service.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikesServiceTest {

    private LikesService likesService;
    @Mock
    private LikesRepository likesRepository;
    @Mock
    private DislikeRepository dislikeRepository;
    @Mock
    private ReviewRepository reviewRepository;
    Review review;
    Like like;
    Dislike dislike;

    @BeforeEach
    public void setUp() {
        likesService = new LikesServiceImpl(likesRepository,dislikeRepository,reviewRepository);
        review = Review.builder()
                .id(1L)
                .authorId(1L)
                .username("User1")
                .title("Title1")
                .content("Content1")
                .createdDateTime(LocalDateTime.now())
                .mark(5L)
                .eventId(1L)
                .likes(new HashSet<>())
                .dislikes(new HashSet<>())
                .build();
        like = Like.builder()
                .review(review)
                .userId(2L)
                .build();
        dislike = Dislike.builder()
                .review(review)
                .userId(3L)
                .build();
    }

    @Test
    void testLikes_goodResult() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(likesRepository.existsByUserIdAndReviewId(anyLong(), anyLong())).thenReturn(false);
        when(dislikeRepository.existsByUserIdAndReviewId(anyLong(), anyLong())).thenReturn(false);
        when(likesRepository.save(any(Like.class))).thenReturn(like);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        likesService.like(2L,1L);

        assertEquals(review.getLikes().size(), 1);
    }

    @Test
    void testLikes_repeatLike() {

        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(likesRepository.existsByUserIdAndReviewId(anyLong(), anyLong())).thenReturn(true);

        assertThrows(BaseRelationshipException.class, () -> likesService.like(2L,1L));
    }

    @Test
    void testDislikes_goodResult() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(dislikeRepository.existsByUserIdAndReviewId(anyLong(), anyLong())).thenReturn(false);
        when(likesRepository.existsByUserIdAndReviewId(anyLong(), anyLong())).thenReturn(false);
        when(dislikeRepository.save(any(Dislike.class))).thenReturn(dislike);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        likesService.dislike(3L,1L);

        assertEquals(review.getDislikes().size(), 1);
    }

    @Test
    void testdislikes_repeat() {

        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(dislikeRepository.existsByUserIdAndReviewId(anyLong(), anyLong())).thenReturn(true);

        assertThrows(BaseRelationshipException.class, () -> likesService.dislike(3L,1L));
    }
}
