package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.javaops.topjava2.model.Dish;

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
