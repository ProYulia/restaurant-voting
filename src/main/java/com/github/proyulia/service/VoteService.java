package com.github.proyulia.service;

import com.github.proyulia.error.IllegalRequestDataException;
import com.github.proyulia.error.VoteTimeoutException;
import com.github.proyulia.mapper.VoteMapper;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.model.Vote;
import com.github.proyulia.repository.RestaurantRepository;
import com.github.proyulia.repository.UserRepository;
import com.github.proyulia.repository.VoteRepository;
import com.github.proyulia.to.VoteRequestTo;
import com.github.proyulia.to.VoteResponseTo;
import com.github.proyulia.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final RestaurantRepository restaurantRepository;

    private final VoteRepository voteRepository;

    private final VoteMapper mapper;

    @Value("${app.votingDeadline}")
    private String deadline;

    @Transactional
    public VoteResponseTo create(VoteRequestTo voteTo, AuthUser authUser) {
        int restaurantId = voteTo.getRestaurantId();
        LocalDate currentDate = LocalDate.now();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("No restaurant with id = " + restaurantId));

        Vote vote = new Vote(currentDate, authUser.getUser(), restaurant);
        if (voteRepository.getByUserIdAndDate(authUser.getUser().id(), currentDate).isEmpty()) {
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
