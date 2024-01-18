package com.github.proyulia.repository;

import com.github.proyulia.model.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    List<Menu> findAllByRestaurantId(int restaurantId);

    void deleteExistedByIdAndRestaurantId(int id, int restaurantId);
}
