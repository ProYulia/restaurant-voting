package com.github.proyulia.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<DishTo> dishes;

    public RestaurantTo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
