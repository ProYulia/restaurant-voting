package com.github.proyulia.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RestaurantRequestTo {

    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
}
