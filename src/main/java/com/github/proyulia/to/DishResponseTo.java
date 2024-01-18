package com.github.proyulia.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DishResponseTo {

    @NotNull
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    private Integer price;
}
