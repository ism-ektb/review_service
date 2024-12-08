package ru.yandex.review_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.review_service.dto.external.registration.RegistrationResponseGetStates;
import ru.yandex.review_service.dto.external.registration.RegistrationState;

import java.util.List;

@FeignClient(name = "registration", url = "http://51.250.41.207:8085")
public interface RegistrationClient {
    @RequestMapping(method = RequestMethod.GET, value = "/registrations/states")
    List<RegistrationResponseGetStates> getWithStates(@RequestHeader("X-Sharer-User-Id") long userId,
                                                      @RequestParam("states") List<RegistrationState> states,
                                                      @RequestParam("eventId") Long eventId);
}
