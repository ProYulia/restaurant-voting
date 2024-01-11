package ru.javaops.topjava2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "dish")
@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Dish extends NamedEntity {

    @Column(nullable = false)
    @NotNull
    @Range(min = 1)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + price + ']';
    }
}
