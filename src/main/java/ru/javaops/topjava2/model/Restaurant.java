package ru.javaops.topjava2.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends NamedEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    public Restaurant(Integer id, String name) {
        super(id, name);

    }

    public Restaurant(Integer id, String name, Menu menu) {
        super(id, name);
        this.menu = menu;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
