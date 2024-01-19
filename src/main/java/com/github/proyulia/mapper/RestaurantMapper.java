package com.github.proyulia.mapper;

import com.github.proyulia.config.MapConfig;
import com.github.proyulia.model.Dish;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.to.RestaurantTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = MenuMapper.class, config = MapConfig.class)
public interface RestaurantMapper {

    @Mapping(target = "name", source = "restaurant.name")
    RestaurantTo toRestaurantTo(Restaurant restaurant, List<Dish> dishes);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Restaurant restaurant, RestaurantTo restaurantTo);

    @Mapping(target = "id", ignore = true)
    Restaurant toRestaurantEntity(RestaurantTo restaurantRequestTo);
}
