package com.github.proyulia.repository;

import com.github.proyulia.model.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    Optional<List<Menu>> findAllByRestaurantId(int restaurantId);

    void deleteExistedByIdAndRestaurantId(int id, int restaurantId);

    Optional<Menu> findByIdAndRestaurantId(int menuId, int restaurantId);
}
