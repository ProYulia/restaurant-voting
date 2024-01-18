package com.github.proyulia.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.proyulia.model.Dish;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponseTo {

    @NotNull
    private Integer id;

    private String name;

    private Integer votes;

    private List<Dish> dishes;
}
