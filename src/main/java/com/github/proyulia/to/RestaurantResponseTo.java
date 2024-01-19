package com.github.proyulia.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponseTo {

    private Integer id;

    private String name;

    private Integer votes;

    private List<DishResponseTo> dishes;
}
