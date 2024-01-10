package ru.javaops.topjava2.web.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.service.MenuService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "menu")
public class UserMenuController {

    static final String REST_URL = "/api/restaurant";
    private final MenuService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllEffective() {
        return service.getAllByDate(LocalDate.now());
    }
}
