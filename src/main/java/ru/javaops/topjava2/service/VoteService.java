package ru.javaops.topjava2.service;

import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteMapper mapper;

    @Value("${app.votingDeadline}")
    private String deadline;

    public VoteResponseTo create(VoteRequestTo voteTo, int userId) {
        checkDeadline(LocalTime.now());
        Restaurant restaurant = restaurantRepository.getExisted(voteTo.getRestaurantId());
        Vote vote = voteRepository.getByUserIdAndDate(userId, LocalDate.now())
                .orElseGet(() -> new Vote(LocalDate.now(), userRepository.getExisted(userId), restaurant));
        vote.setRestaurant(restaurant);
        Vote persisted = voteRepository.save(vote);
        return mapper.entityToVoteResponseTo(persisted);
    }

    private void checkDeadline(LocalTime currentTime) {
        if (currentTime.isAfter(LocalTime.parse(deadline))) throw new VoteTimeoutException(deadline);
    }
}
