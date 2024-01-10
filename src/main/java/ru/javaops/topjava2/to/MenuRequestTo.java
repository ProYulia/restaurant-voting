package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MenuRequestTo {

    @NotNull
    private LocalDate date;
}
