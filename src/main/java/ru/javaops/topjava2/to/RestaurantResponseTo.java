package ru.javaops.topjava2.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Menu;

import java.util.List;

@Getter
@Setter
public class RestaurantResponseTo {

    @NotNull
    private Integer id;

    private String name;

    private Integer votes;

    private List<Dish> dishes;
}
