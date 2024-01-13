package ru.javaops.topjava2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "date_effective")
    @NotNull
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
