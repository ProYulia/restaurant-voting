package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.mapper.RestaurantMapper;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.RestaurantRequestTo;
import ru.javaops.topjava2.to.RestaurantResponseTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    private final RestaurantMapper mapper;

    private final VoteRepository voteRepository;

    private final DishRepository dishRepository;

    @Transactional
    public RestaurantResponseTo create(RestaurantRequestTo restaurantTo) {
        Restaurant restaurant = mapper.requestToRestaurantEntity(restaurantTo);
        Restaurant persisted = repository.save(restaurant);
        return mapper.entityToRestaurantResponseTo(persisted, 0, null);
    }

    public List<RestaurantResponseTo> getAllByDate(LocalDate today) {
        Map<Integer, Integer> votes = voteRepository.getTotal();
        Map<Integer, List<Dish>> dishes = dishRepository.getRestaurantDishes();
        return repository.findAllFilteredByDate(today).stream()
                .map(r -> mapper.entityToRestaurantResponseTo(r, votes.getOrDefault(r.getId(), 0), dishes.getOrDefault(r.getId(), null)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(RestaurantRequestTo restaurantTo, int id) {
        Restaurant restaurant = repository.getExisted(id);
        mapper.updateEntity(restaurant, restaurantTo);
        repository.save(restaurant);
    }

    public List<RestaurantResponseTo> getAll() {
        Map<Integer, Integer> votes = voteRepository.getTotal();
        Map<Integer, List<Dish>> dishes = dishRepository.getRestaurantDishes();
        return repository.findAll().stream()
                .map(r -> mapper.entityToRestaurantResponseTo(r, votes.getOrDefault(r.getId(), 0), dishes.getOrDefault(r.getId(), null)))
                .collect(Collectors.toList());
    }
}
