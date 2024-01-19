package com.github.proyulia.web.admin;

import com.github.proyulia.service.RestaurantService;
import com.github.proyulia.to.RestaurantTo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "admin-restaurant-controller")
public class AdminRestaurantController {

    static final String REST_URL = "/api/admin/restaurant";

    private final RestaurantService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantTo> create(@Valid @RequestBody RestaurantTo restaurant) {

        RestaurantTo created = service.create(restaurant);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id,
                       @Valid @RequestBody RestaurantTo restaurant) {

        service.update(restaurant, id);
    }

    @GetMapping(value = "/restaurants-with-menus",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAllWithMenus() {
        return service.getAllWithMenus();
    }

    @GetMapping(value = "/restaurants",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo get(@PathVariable int id) {
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
