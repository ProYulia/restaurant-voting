package ru.javaops.topjava2.to;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DishRequestTo {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    private Integer price;
}
