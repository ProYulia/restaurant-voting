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
import com.github.proyulia.to.RestaurantRequestTo;
import com.github.proyulia.to.RestaurantResponseTo;
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
    public RestaurantResponseTo create(RestaurantRequestTo restaurantTo) {
        Restaurant persisted = repository.save(mapper.requestToRestaurantEntity(restaurantTo));
        return mapper.entityToRestaurantResponseTo(persisted, 0, null);
    }

    @Cacheable
    public List<RestaurantResponseTo> getAllByDate(LocalDate date) {
        Map<Integer, Integer> votes = voteRepository.getTotal();
        Map<Integer, List<Dish>> dishes = dishRepository.getRestaurantDishes();
        return repository.findAllFilteredByDate(date).stream()
                .map(r -> mapper.entityToRestaurantResponseTo(r, votes.getOrDefault(r.getId(), 0), dishes.getOrDefault(r.getId(), null)))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "restaurants", key = "#id")
    @Transactional
    public void update(RestaurantRequestTo restaurantTo, int id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        mapper.updateEntity(restaurant, restaurantTo);
        repository.save(restaurant);
    }

    public List<RestaurantResponseTo> getAllWithMenus() {
        Map<Integer, Integer> votes = voteRepository.getTotal();
        Map<Integer, List<Dish>> dishes = dishRepository.getRestaurantDishes();
        return repository.findAll().stream()
                .map(r -> mapper.entityToRestaurantResponseTo(r, votes.getOrDefault(r.getId(), 0), dishes.getOrDefault(r.getId(), null)))
                .collect(Collectors.toList());
    }

    public List<RestaurantResponseTo> getAll() {
        return repository.findAll().stream()
                .map(r -> mapper.entityToRestaurantResponseTo(r, null, null))
                .collect(Collectors.toList());
    }

    public RestaurantResponseTo get(int id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        return mapper.entityToRestaurantResponseTo(restaurant, null, null);
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
