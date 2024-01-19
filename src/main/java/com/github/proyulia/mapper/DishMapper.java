package com.github.proyulia.mapper;

import com.github.proyulia.config.MapConfig;
import com.github.proyulia.model.Dish;
import com.github.proyulia.model.Menu;
import com.github.proyulia.to.DishTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(uses = MenuMapper.class, config = MapConfig.class)
public interface DishMapper {

    DishTo toDishTo(Dish dish);

    @Mapping(target = "menu", source = "menu")
    @Mapping(target = "id", ignore = true)
    Dish toDishEntity(DishTo dishTo, Menu menu);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Dish dish, DishTo dishTo);
}