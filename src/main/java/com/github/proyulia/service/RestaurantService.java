package com.github.proyulia.service;

import com.github.proyulia.error.DeletionNotAllowedException;
import com.github.proyulia.error.ErrorType;
import com.github.proyulia.error.NotFoundException;
import com.github.proyulia.mapper.RestaurantMapper;
import com.github.proyulia.model.Dish;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.repository.DishRepository;
import com.github.proyulia.repository.RestaurantRepository;
import com.github.proyulia.repository.VoteRepository;
import com.github.proyulia.to.RestaurantTo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "restaurants")
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    private final RestaurantMapper mapper;

    private final VoteRepository voteRepository;

    private final DishRepository dishRepository;

    @CacheEvict(value = "restaurants")
    @Transactional
    public RestaurantTo create(RestaurantTo restaurantTo) {
        Restaurant persisted =
                repository.save(mapper.toRestaurantEntity(restaurantTo));
        return mapper.toRestaurantTo(persisted, null);
    }

    @Cacheable
    public List<RestaurantTo> getAllByDate(LocalDate date) {
        Map<Integer, List<Dish>> dishes = dishRepository.getRestaurantDishes();
        return repository.findAllFilteredByDate(date).stream()
                .map(r -> mapper.toRestaurantTo(r,
                        dishes.getOrDefault(r.getId(), null)))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "restaurants", key = "#id")
    @Transactional
    public void update(RestaurantTo restaurantTo, int id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        mapper.updateEntity(restaurant, restaurantTo);
        repository.save(restaurant);
    }

    public List<RestaurantTo> getAllWithMenus() {
        Map<Integer, List<Dish>> dishes = dishRepository.getRestaurantDishes();
        return repository.findAll().stream()
                .map(r -> mapper.toRestaurantTo(r,
                        dishes.getOrDefault(r.getId(), null)))
                .collect(Collectors.toList());
    }

    public List<RestaurantTo> getAll() {
        return repository.findAll().stream()
                .map(r -> mapper.toRestaurantTo(r, null))
                .collect(Collectors.toList());
    }

    public RestaurantTo get(int id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        return mapper.toRestaurantTo(restaurant, null);
    }

    @CacheEvict(value = "restaurants", key = "#id")
    public void delete(int id) {
        isDeletionAllowed(id);
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        repository.deleteExisted(id);
    }

    private void isDeletionAllowed(int id) {
        if (voteRepository.countVotesByRestaurantId(id) != 0) {
            throw new DeletionNotAllowedException();
        }
    }
}
