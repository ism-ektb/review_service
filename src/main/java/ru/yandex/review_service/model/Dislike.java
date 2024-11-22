package ru.yandex.review_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dislikes")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
