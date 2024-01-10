package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RestaurantRequestTo {
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;
}
