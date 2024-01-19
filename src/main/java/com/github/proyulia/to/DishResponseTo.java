package com.github.proyulia.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DishResponseTo {

    private Integer id;

    private String name;

    private Integer price;
}
