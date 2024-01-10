package ru.javaops.topjava2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javaops.topjava2.config.MapConfig;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantRequestTo;
import ru.javaops.topjava2.to.RestaurantResponseTo;

@Mapper(config = MapConfig.class)
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menus", ignore = true)
    Restaurant requestToRestaurantEntity(RestaurantRequestTo restaurantRequestTo);

    RestaurantResponseTo entityToRestaurantResponseTo(Restaurant restaurant, Integer votes);
}
