package com.github.proyulia.web.admin;

import com.github.proyulia.service.DishService;
import com.github.proyulia.to.DishTo;
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
@RequestMapping(value = AdminDishController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "admin-dish-controller")
public class AdminDishController {
    static final String REST_URL = "/api/admin/restaurant/{restaurantId}/menu" +
            "/{menuId}/dish";

    private final DishService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DishTo> create(@Valid @RequestBody DishTo dishTo,
                                         @PathVariable int menuId,
                                         @PathVariable int restaurantId) {

        DishTo created = service.create(dishTo, menuId, restaurantId);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(restaurantId, menuId, created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo,
                       @PathVariable int id,
                       @PathVariable int menuId,
                       @PathVariable int restaurantId) {

        service.update(dishTo, id, menuId, restaurantId);
    }

    @GetMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DishTo> getAll(@PathVariable int menuId,
                               @PathVariable int restaurantId) {

        return service.getAll(menuId, restaurantId);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DishTo get(@PathVariable int id,
                      @PathVariable int menuId,
                      @PathVariable int restaurantId) {

        return service.get(id, menuId, restaurantId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id,
                       @PathVariable int menuId,
                       @PathVariable int restaurantId) {

        service.delete(id, menuId, restaurantId);
    }
}
