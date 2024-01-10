package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MenuResponseTo {

    @NotNull
    private Integer id;

    @NotNull
    private LocalDate date;
}
