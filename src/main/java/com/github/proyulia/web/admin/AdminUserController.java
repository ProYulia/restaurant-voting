package com.github.proyulia.web.admin;

import com.github.proyulia.model.User;
import com.github.proyulia.util.validation.ValidationUtil;
import com.github.proyulia.web.AbstractUserController;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces =
        MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractUserController {

    static final String REST_URL = "/api/admin/users";

    @GetMapping
    public List<User> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        ValidationUtil.checkNew(user);
        User created = repository.prepareAndSave(user);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(user, id);
        repository.prepareAndSave(user);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        User user = repository.getExisted(id);
        user.setEnabled(enabled);
    }
}