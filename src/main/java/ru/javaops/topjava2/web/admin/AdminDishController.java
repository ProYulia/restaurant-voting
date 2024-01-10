package ru.javaops.topjava2.web.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.service.DishService;
import ru.javaops.topjava2.to.DishRequestTo;
import ru.javaops.topjava2.to.DishResponseTo;

import java.net.URI;

@RestController
@RequestMapping(value = AdminDishController.REST_URL)
@RequiredArgsConstructor
public class AdminDishController {
    static final String REST_URL = "/api/admin/restaurant/{restaurantId}/menu/{menuId}/dish";

    private final DishService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DishResponseTo> create(@Valid @RequestBody DishRequestTo dish,
                                                 @PathVariable int menuId,
                                                 @PathVariable int restaurantId) {

        DishResponseTo created = service.create(dish, menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menuId, created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
