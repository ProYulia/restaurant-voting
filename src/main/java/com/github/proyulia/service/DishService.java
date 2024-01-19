package com.github.proyulia.service;

import com.github.proyulia.error.DeletionNotAllowedException;
import com.github.proyulia.error.ErrorType;
import com.github.proyulia.error.NotFoundException;
import com.github.proyulia.mapper.DishMapper;
import com.github.proyulia.model.Dish;
import com.github.proyulia.model.Menu;
import com.github.proyulia.repository.DishRepository;
import com.github.proyulia.repository.MenuRepository;
import com.github.proyulia.to.DishTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    private final MenuRepository menuRepository;

    private final DishMapper mapper;

    @Transactional
    public DishTo create(DishTo dishTo, int menuId, int restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId,
                        restaurantId).
                orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        Dish persisted = dishRepository.save(mapper.toDishEntity(dishTo, menu));
        return mapper.toDishTo(persisted);
    }

    @Transactional
    public void update(DishTo dishTo, int id, int menuId, int restaurantId) {

        Dish dish = dishRepository.findByIdAndMenuIdAndMenuRestaurantId(id,
                        menuId, restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        mapper.updateEntity(dish, dishTo);
        dishRepository.save(dish);
    }

    public List<DishTo> getAll(int menuId, int restaurantId) {
        return dishRepository.findAllByMenuIdAndMenuRestaurantId(menuId,
                        restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title))
                .stream()
                .map(mapper::toDishTo)
                .collect(Collectors.toList());
    }

    public DishTo get(int id, int menuId, int restaurantId) {
        return mapper.toDishTo(dishRepository.findByIdAndMenuIdAndMenuRestaurantId(id, menuId, restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title)));
    }

    public void delete(int id, int menuId, int restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        isDeletionAllowed(menu);
        dishRepository.deleteExistedByIdAndMenuIdAndMenuRestaurantId(id,
                menuId, restaurantId);
    }

    private void isDeletionAllowed(Menu menu) {
        if (menu.getDate().isBefore(LocalDate.now())) {
            throw new DeletionNotAllowedException();
        }
    }
}
