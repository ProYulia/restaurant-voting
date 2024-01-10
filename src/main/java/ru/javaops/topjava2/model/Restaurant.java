package ru.javaops.topjava2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Menu> menus;
}
