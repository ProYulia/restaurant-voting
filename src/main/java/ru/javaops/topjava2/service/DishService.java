package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.mapper.DishMapper;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.to.DishRequestTo;
import ru.javaops.topjava2.to.DishResponseTo;


@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;
    private final DishMapper mapper;

    @Transactional
    public DishResponseTo create(DishRequestTo dishTo, int menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("No menu with id = " + menuId));

        Dish entity = mapper.requestToDishEntity(dishTo, menu);
        Dish persisted = dishRepository.save(entity);
        return mapper.entityToDishResponse(persisted);
    }

    @Transactional
    public DishResponseTo update(DishRequestTo dishRequestTo, int id, int menuId) {
        menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("No menu with id = " + menuId));
        Dish dish = dishRepository.getExisted(id);
        mapper.updateEntity(dish, dishRequestTo);
        Dish persisted = dishRepository.save(dish);
        return mapper.entityToDishResponse(persisted);
    }
}
