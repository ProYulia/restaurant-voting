package com.github.proyulia.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MenuTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @NotNull
    private LocalDate date;

    public MenuTo(LocalDate date) {
        this.date = date;
    }
}
