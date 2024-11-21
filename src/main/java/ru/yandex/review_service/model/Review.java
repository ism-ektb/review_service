package ru.yandex.review_service.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reviews")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author_id")
    private Long authorId;
    private String username;
    private String title;
    @Column(name = "content_text")
    private String content;
    @Column(name = "create_time")
    @Builder.Default()
    private LocalDateTime createdDateTime = LocalDateTime.now();
    @Column(name = "update_time")
    private LocalDateTime updatedDateTime;
    private Long mark;
    @Column(name = "event_id")
    private Long eventId;
    @OneToMany(mappedBy="review", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dislike> dislikes = new HashSet<>();
}
