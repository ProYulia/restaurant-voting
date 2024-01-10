package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MenuCreationTo {

    @NotNull
    private Integer id;

    @NotNull
    private LocalDate date;
}
