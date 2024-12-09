package ru.yandex.review_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.yandex.review_service.dto.external.EventDto;

@FeignClient(name = "event", url = "http://51.250.41.207:8082")
public interface EventClient {
    @RequestMapping(method = RequestMethod.GET, value = "/events/{eventId}")
    EventDto getById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("eventId") Long eventId);
}
