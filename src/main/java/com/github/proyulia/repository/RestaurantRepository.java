package com.github.proyulia.repository;

import com.github.proyulia.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query(value = "SELECT r FROM Restaurant r INNER JOIN Menu m ON r = m" +
            ".restaurant WHERE m.date =:date")
    List<Restaurant> findAllFilteredByDate(@Param("date") LocalDate date);
}