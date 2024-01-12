package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    default Map<Integer, List<Dish>> getRestaurantDishes() {
        return getDishesByRestaurant().stream()
                .collect(Collectors.groupingBy(o -> (Integer) o[0], Collectors.mapping(o -> new Dish((String) o[1], (Integer) o[2]), Collectors.toList())));
    }

    ;

    @Query(value = "SELECT restaurant_id, d.name, d.price FROM dish d JOIN menu m on d.menu_id = m.id WHERE date_created = current_date",
            nativeQuery = true)
    List<Object[]> getDishesByRestaurant();
}
