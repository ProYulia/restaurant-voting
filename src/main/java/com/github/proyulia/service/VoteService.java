package com.github.proyulia.service;

import com.github.proyulia.error.IllegalRequestDataException;
import com.github.proyulia.error.VoteTimeoutException;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.model.Vote;
import com.github.proyulia.repository.RestaurantRepository;
import com.github.proyulia.to.VoteRequestTo;
import com.github.proyulia.to.VoteResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.proyulia.mapper.VoteMapper;
import com.github.proyulia.repository.UserRepository;
import com.github.proyulia.repository.VoteRepository;

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
        int restaurantId = voteTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("No restaurant with id = " + restaurantId));

        Vote vote = new Vote(LocalDate.now(), userRepository.getExisted(userId), restaurant);
        if (voteRepository.getByUserIdAndDate(userId, LocalDate.now()).isEmpty()) {
            Vote persisted = voteRepository.save(vote);
            return mapper.entityToVoteResponseTo(persisted);
        } else {
            throw new IllegalRequestDataException("Vote already exists");
        }
    }

    public void update(VoteRequestTo voteTo, int userId) {
        checkDeadline(LocalTime.now());
        int restaurantId = voteTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("No restaurant with id = " + restaurantId));
        Vote vote = voteRepository.getByUserIdAndDate(userId, LocalDate.now())
                .orElseThrow(() -> new IllegalRequestDataException("You should vote first"));
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }


    private void checkDeadline(LocalTime currentTime) {
        if (currentTime.isAfter(LocalTime.parse(deadline))) throw new VoteTimeoutException(deadline);
    }
}
