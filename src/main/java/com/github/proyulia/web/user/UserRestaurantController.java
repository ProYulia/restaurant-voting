package com.github.proyulia.web.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.proyulia.service.RestaurantService;
import com.github.proyulia.to.RestaurantResponseTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "user-restaurant-controller")
public class UserRestaurantController {
    static final String REST_URL = "/api/restaurants/current-menu";

    private final RestaurantService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantResponseTo> getAllEffective() {
        return service.getAllByDate(LocalDate.now());
    }
}
