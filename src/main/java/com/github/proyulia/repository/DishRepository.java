package com.github.proyulia.repository;

import com.github.proyulia.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    default Map<Integer, List<Dish>> getRestaurantDishes() {
        return getDishesByRestaurant().stream()
                .collect(Collectors.groupingBy(o -> (Integer) o[0],
                        Collectors.mapping(o -> new Dish((String) o[1],
                                (Integer) o[2]), Collectors.toList())));
    }

    @Query(value = "SELECT m.restaurant.id, d.name, d.price FROM Dish d JOIN " +
            "Menu m on d.menu.id = m.id WHERE m.date = current_date")
    List<Object[]> getDishesByRestaurant();

    Optional<List<Dish>> findAllByMenuIdAndMenuRestaurantId(int menuId,
                                                            int restaurantId);

    void deleteExistedByIdAndMenuIdAndMenuRestaurantId(int id, int menuId,
                                                       int restaurantId);

    Optional<Dish> findByIdAndMenuIdAndMenuRestaurantId(int id, int menuId,
                                                        int restaurantId);
}
