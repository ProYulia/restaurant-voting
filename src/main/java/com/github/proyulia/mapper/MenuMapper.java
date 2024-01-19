package com.github.proyulia.mapper;

import com.github.proyulia.config.MapConfig;
import com.github.proyulia.model.Menu;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.to.MenuTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapConfig.class)
public interface MenuMapper {
    MenuTo toMenuTo(Menu menu);

    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "id", ignore = true)
    Menu toMenuEntity(MenuTo menuTo, Restaurant restaurant);

    void updateEntity(@MappingTarget Menu menu, MenuTo menuTo);
}
