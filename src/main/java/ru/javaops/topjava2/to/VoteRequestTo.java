package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class VoteRequestTo {

    @NotNull
    private Integer restaurantId;
}
