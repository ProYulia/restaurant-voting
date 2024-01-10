package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.mapper.RestaurantMapper;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.RestaurantRequestTo;
import ru.javaops.topjava2.to.RestaurantResponseTo;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    public RestaurantResponseTo create(RestaurantRequestTo restaurantTo) {
        Restaurant restaurant = mapper.requestToRestaurantEntity(restaurantTo);
        Restaurant persisted = repository.save(restaurant);
        return mapper.entityToRestaurantResponse(persisted);
    }
}
