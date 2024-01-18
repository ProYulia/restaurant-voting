package com.github.proyulia.web.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.proyulia.service.DishService;
import com.github.proyulia.to.DishRequestTo;
import com.github.proyulia.to.DishResponseTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminDishController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "admin-dish-controller")
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishRequestTo dishRequestTo,
                       @PathVariable int id,
                       @PathVariable int menuId,
                       @PathVariable int restaurantId) {

        service.update(dishRequestTo, id, menuId);
    }

    @GetMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DishResponseTo> getAll(@PathVariable int menuId, @PathVariable int restaurantId) {
        return service.getAll(menuId, restaurantId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id,
                       @PathVariable int menuId,
                       @PathVariable int restaurantId) {
        service.delete(id, menuId, restaurantId);
    }
}
