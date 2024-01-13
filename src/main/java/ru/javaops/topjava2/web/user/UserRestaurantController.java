package ru.javaops.topjava2.web.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.service.RestaurantService;
import ru.javaops.topjava2.to.RestaurantResponseTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "user-restaurant-controller")
public class UserRestaurantController {
    static final String REST_URL = "/api/restaurant";

    private final RestaurantService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantResponseTo> getAllEffective() {
        return service.getAllByDate(LocalDate.now());
    }
}
