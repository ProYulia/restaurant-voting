package ru.javaops.topjava2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.javaops.topjava2.config.MapConfig;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.to.DishRequestTo;
import ru.javaops.topjava2.to.DishResponseTo;


@Mapper(uses = MenuMapper.class, config = MapConfig.class)
public interface DishMapper {

    DishResponseTo entityToDishResponse(Dish dish);

    @Mapping(target = "menu", source = "menu")
    @Mapping(target = "id", ignore = true)
    Dish requestToDishEntity(DishRequestTo dishRequestTo, Menu menu);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Dish dish, DishRequestTo dishRequestTo);
}