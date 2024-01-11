package ru.javaops.topjava2.web.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.service.MenuService;
import ru.javaops.topjava2.to.MenuCreationTo;
import ru.javaops.topjava2.to.MenuRequestTo;
import ru.javaops.topjava2.to.MenuResponseTo;

import java.net.URI;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "admin-menu-controller")
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaurant/{restaurantId}/menu";
    private final MenuService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MenuResponseTo> create(@Valid @RequestBody MenuRequestTo menu,
                                                 @PathVariable int restaurantId) {

        MenuResponseTo created = service.create(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuResponseTo update(@PathVariable int restaurantId,
                                 @PathVariable int id,
                                 @Valid @RequestBody MenuRequestTo menuRequestTo) {

        return service.update(menuRequestTo, id, restaurantId);
    }
}
