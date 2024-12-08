package ru.yandex.review_service.dto.external.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseGetStates {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Long eventId;
    private RegistrationState state;
    private LocalDateTime createdDateTime;
}