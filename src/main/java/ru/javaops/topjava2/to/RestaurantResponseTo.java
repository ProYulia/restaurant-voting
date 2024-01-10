package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseTo {

    @NotNull
    private Integer id;

    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    private Integer votes;
}
