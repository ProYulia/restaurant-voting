package ru.javaops.topjava2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.javaops.topjava2.config.MapConfig;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.MenuRequestTo;
import ru.javaops.topjava2.to.MenuResponseTo;

@Mapper(config = MapConfig.class)
public interface MenuMapper {
    MenuResponseTo entityToMenuResponse(Menu menu);

    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "id", ignore = true)
    Menu requestToMenuEntity(MenuRequestTo menuTo, Restaurant restaurant);

    void updateEntity(@MappingTarget Menu menu, MenuRequestTo menuRequestTo);
}
