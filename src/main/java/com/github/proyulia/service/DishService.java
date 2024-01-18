package com.github.proyulia.service;

import com.github.proyulia.error.DeletionNotAllowedException;
import com.github.proyulia.error.NotFoundException;
import com.github.proyulia.model.Dish;
import com.github.proyulia.model.Menu;
import com.github.proyulia.to.DishRequestTo;
import com.github.proyulia.to.DishResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.proyulia.mapper.DishMapper;
import com.github.proyulia.repository.DishRepository;
import com.github.proyulia.repository.MenuRepository;

import java.time.LocalDate;
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

    public void delete(int id, int menuId, int restaurantId) {
        Menu menu = menuRepository.getExisted(menuId);
        isDeletionAllowed(menu);
        dishRepository.deleteExistedByIdAndMenuIdAndMenuRestaurantId(id, menuId, restaurantId);
    }

    private void isDeletionAllowed(Menu menu) {
        if (menu.getDate().isBefore(LocalDate.now())) {
            throw new DeletionNotAllowedException();
        }
    }
}
