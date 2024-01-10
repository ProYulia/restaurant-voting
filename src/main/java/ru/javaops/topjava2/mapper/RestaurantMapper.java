package ru.javaops.topjava2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javaops.topjava2.config.MapConfig;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantResponseTo;
import ru.javaops.topjava2.to.RestaurantRequestTo;

@Mapper(config = MapConfig.class)
public interface RestaurantMapper {

    RestaurantResponseTo entityToRestaurantResponse(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    Restaurant requestToRestaurantEntity(RestaurantRequestTo restaurantRequestTo);
}
