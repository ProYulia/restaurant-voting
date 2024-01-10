package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RestaurantResponseTo {

    @NotNull
    private Integer id;

    @NotBlank
    @Size(min = 5, max = 50)
    private String name;
}
