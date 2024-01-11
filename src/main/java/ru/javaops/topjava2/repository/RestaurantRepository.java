package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query(value = "SELECT r, d FROM Restaurant r INNER JOIN Menu m ON r = m.restaurant WHERE m.date =:today")
    List<Restaurant> findAllFilteredByDate(LocalDate today);
}