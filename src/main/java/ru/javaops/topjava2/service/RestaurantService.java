package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.mapper.RestaurantMapper;
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

    public RestaurantResponseTo create(RestaurantRequestTo restaurantTo) {
        Restaurant restaurant = mapper.requestToRestaurantEntity(restaurantTo);
        Restaurant persisted = repository.save(restaurant);
        return mapper.entityToRestaurantResponseTo(persisted, 0);
    }

    public List<RestaurantResponseTo> getAllByDate(LocalDate today) {
        Map<Integer, Integer> votes = voteRepository.getTotal();
        return repository.findAllFilteredByDate(today).stream()
                .map(r -> mapper.entityToRestaurantResponseTo(r, votes.getOrDefault(r.getId(), 0)))
                .collect(Collectors.toList());
    }
}
