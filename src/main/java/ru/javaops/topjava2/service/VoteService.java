package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.error.VoteTimeoutException;
import ru.javaops.topjava2.mapper.VoteMapper;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.UserRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.VoteRequestTo;
import ru.javaops.topjava2.to.VoteResponseTo;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class VoteService {

    private static final LocalTime DEADLINE = LocalTime.of(11, 0);
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteMapper mapper;

    public VoteResponseTo create(VoteRequestTo voteTo, int userId) {
        checkDeadline(LocalTime.now());
        Restaurant restaurant = restaurantRepository.getOne(voteTo.getRestaurantId());
        Vote vote = voteRepository.getByUserIdAndDate(userId, LocalDate.now())
                .orElseGet(() -> new Vote(LocalDate.now(), userRepository.getOne(userId), restaurant));
        vote.setRestaurant(restaurant);
        Vote persisted = voteRepository.save(vote);
        return mapper.entityToVoteResponseTo(persisted);
    }

    private void checkDeadline(LocalTime currentTime) {
        if (currentTime.isAfter(DEADLINE)) throw new VoteTimeoutException(DEADLINE);
    }
}
