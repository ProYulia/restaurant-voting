package com.github.proyulia.to;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MenuRequestTo {

    @NotNull
    private LocalDate date;
}
