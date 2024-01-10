package ru.javaops.topjava2.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
