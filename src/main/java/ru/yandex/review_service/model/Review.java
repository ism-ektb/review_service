package ru.yandex.review_service.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@SuperBuilder
@Getter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authorId;
    private String username;
    private String title;
    @Column(name = "content_txt")
    private String content;
    @Column(name = "create_time")
    @Builder.Default()
    private LocalDateTime createdDateTime = LocalDateTime.now();
    @Column(name = "update_time")
    private LocalDateTime updatedDateTime;
    private Long mark;
    private Long eventId;
}
