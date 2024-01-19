package com.github.proyulia.web.admin;

import com.github.proyulia.service.MenuService;
import com.github.proyulia.to.MenuTo;
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
@RequestMapping(value = AdminMenuController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "admin-menu-controller")
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaurant/{restaurantId}/menu";

    private final MenuService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MenuTo> create(@Valid @RequestBody MenuTo menuTo,
                                         @PathVariable int restaurantId) {

        MenuTo created = service.create(menuTo, restaurantId);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(restaurantId, created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId,
                       @PathVariable int id,
                       @Valid @RequestBody MenuTo menuTo) {

        service.update(menuTo, id, restaurantId);
    }

    @GetMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuTo> getAll(@PathVariable int restaurantId) {
        return service.getAll(restaurantId);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuTo get(@PathVariable int id, @PathVariable int restaurantId) {
        return service.get(id, restaurantId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        service.delete(id, restaurantId);
    }
}
