package ru.yandex.review_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
