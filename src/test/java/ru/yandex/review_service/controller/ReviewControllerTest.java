package ru.yandex.review_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.review_service.model.ReviewFullOutDto;
import ru.yandex.review_service.model.ReviewInDto;
import ru.yandex.review_service.model.ReviewOutDto;
import ru.yandex.review_service.model.ReviewPatchDto;
import ru.yandex.review_service.service.LikesService;
import ru.yandex.review_service.service.ReviewService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ReviewControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ReviewService service;
    @MockBean
    private LikesService likesService;

    @Test
    @SneakyThrows
    void createUser_goodResult() {
        ReviewInDto reviewInDto = ReviewInDto.builder().username("Ivan").title("Review")
                .content("Review text").eventId(1L).build();
        ReviewFullOutDto reviewOutDto = ReviewFullOutDto.builder().id(1L).build();
        when(service.create(anyLong(), any())).thenReturn(reviewOutDto);
        mvc.perform(post("/reviews").header("userId", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(mapper.writeValueAsString(reviewInDto))).andExpect(status().is(201));
    }

    @Test
    @SneakyThrows
    void patchReview_goodResult() {
        ReviewPatchDto reviewPatchDto = ReviewPatchDto.builder().username("Ivan").build();
        ReviewFullOutDto reviewFullOutDto = ReviewFullOutDto.builder().id(1L).build();
        when(service.patch(anyLong(), anyLong(), any())).thenReturn(reviewFullOutDto);
        mvc.perform(patch("/reviews/{id}", 1L).header("userId", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(mapper.writeValueAsString(reviewPatchDto))).andExpect(status().is(200));
    }


    @Test
    @SneakyThrows
    void getUser_goodResult() {
        ReviewOutDto reviewOutDto = ReviewOutDto.builder().id(1L).build();
        when(service.getReview(anyLong())).thenReturn(reviewOutDto);
        mvc.perform(get("/reviews/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)).andExpect(status().is(200));

    }

    @Test
    @SneakyThrows
    void getAllUser_goodResult() {
        ReviewOutDto reviewOutDto = ReviewOutDto.builder().id(1L).build();
        when(service.findReviewByEventId(anyLong(), any())).thenReturn(List.of(reviewOutDto));
        mvc.perform(get("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .param("eventId", "1")).andExpect(status().is(200));
    }

    @Test
    void like_goodResult() throws Exception {
        ReviewOutDto reviewOutDto = ReviewOutDto.builder().id(1L).build();
        doNothing().when(likesService).like(anyLong(),anyLong());
        mvc.perform(post("/reviews/like/{reviewId}", 1L)
                        .header("userId", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void dislike_goodResult() throws Exception {
        ReviewOutDto reviewOutDto = ReviewOutDto.builder().id(1L).build();
        doNothing().when(likesService).like(anyLong(),anyLong());
        mvc.perform(post("/reviews/dislike/{reviewId}", 1L)
                        .header("userId", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}