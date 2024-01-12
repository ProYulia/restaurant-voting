package ru.javaops.topjava2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Column(name = "date_created", nullable = false)
    @NotNull
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    public Menu(LocalDate date) {
        this.date = date;
    }

    public Menu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + date + ']';
    }
}
