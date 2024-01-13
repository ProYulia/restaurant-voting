package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import java.util.List;
import java.util.stream.Collectors;


@Service
@CacheConfig(cacheNames = "dishes")
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    private final MenuRepository menuRepository;

    private final DishMapper mapper;

    @CacheEvict(allEntries = true)
    @Transactional
    public DishResponseTo create(DishRequestTo dishTo, int menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("No menu with id = " + menuId));

        Dish entity = mapper.requestToDishEntity(dishTo, menu);
        Dish persisted = dishRepository.save(entity);
        return mapper.entityToDishResponse(persisted);
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void update(DishRequestTo dishRequestTo, int id, int menuId) {
        menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("No menu with id = " + menuId));
        Dish dish = dishRepository.getExisted(id);
        mapper.updateEntity(dish, dishRequestTo);
        dishRepository.save(dish);
    }

    @Cacheable
    public List<DishResponseTo> getAll(int menuId, int restaurantId) {
        return dishRepository.findAllByMenuIdAndMenuRestaurantId(menuId, restaurantId).stream()
                .map(mapper::entityToDishResponse)
                .collect(Collectors.toList());
    }
}
