package com.github.proyulia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"date_effective"
        , "restaurant_id"}, name =
        "menus_unique_date_effective_restaurant_id_idx")})
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Menu extends BaseEntity {

    @Column(name = "date_effective", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @ToString.Exclude
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Dish> dishes;

    public Menu(LocalDate date) {
        this.date = date;
    }

    public Menu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }
}
