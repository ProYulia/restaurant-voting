package ru.javaops.topjava2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.javaops.topjava2.config.MapConfig;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantRequestTo;
import ru.javaops.topjava2.to.RestaurantResponseTo;

import java.util.List;

@Mapper(uses = MenuMapper.class, config = MapConfig.class)
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menu", ignore = true)
    Restaurant requestToRestaurantEntity(RestaurantRequestTo restaurantRequestTo);

    @Mapping(target = "name", source = "restaurant.name")
    RestaurantResponseTo entityToRestaurantResponseTo(Restaurant restaurant,
                                                      Integer votes,
                                                      List<Dish> dishes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menu", ignore = true)
    void updateEntity(@MappingTarget Restaurant restaurant,
                      RestaurantRequestTo restaurantRequestTo);
}
