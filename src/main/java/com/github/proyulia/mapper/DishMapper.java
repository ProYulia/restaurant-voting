package com.github.proyulia.mapper;

import com.github.proyulia.config.MapConfig;
import com.github.proyulia.model.Dish;
import com.github.proyulia.model.Menu;
import com.github.proyulia.to.DishRequestTo;
import com.github.proyulia.to.DishResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(uses = MenuMapper.class, config = MapConfig.class)
public interface DishMapper {

    DishResponseTo entityToDishResponse(Dish dish);

    @Mapping(target = "menu", source = "menu")
    @Mapping(target = "id", ignore = true)
    Dish requestToDishEntity(DishRequestTo dishRequestTo, Menu menu);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Dish dish, DishRequestTo dishRequestTo);
}