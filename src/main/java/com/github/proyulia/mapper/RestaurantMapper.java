package com.github.proyulia.mapper;

import com.github.proyulia.config.MapConfig;
import com.github.proyulia.model.Dish;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.to.RestaurantRequestTo;
import com.github.proyulia.to.RestaurantResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
