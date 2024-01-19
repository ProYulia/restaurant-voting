package com.github.proyulia.service;

import com.github.proyulia.error.ErrorType;
import com.github.proyulia.error.IllegalRequestDataException;
import com.github.proyulia.mapper.VoteMapper;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.model.Vote;
import com.github.proyulia.repository.RestaurantRepository;
import com.github.proyulia.repository.VoteRepository;
import com.github.proyulia.to.VoteTo;
import com.github.proyulia.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@CacheConfig(cacheNames = "votes")
@RequiredArgsConstructor
public class VoteService {

    private final RestaurantRepository restaurantRepository;

    private final VoteRepository voteRepository;

    private final VoteMapper mapper;

    @Value("${app.votingDeadline}")
    private LocalTime deadline;

    @Cacheable
    @Transactional
    public VoteTo create(VoteTo voteTo, AuthUser authUser) {
        int restaurantId = voteTo.getRestaurantId();
        LocalDate currentDate = LocalDate.now();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException(ErrorType.BAD_REQUEST.title));

        Vote vote = new Vote(currentDate, authUser.getUser(), restaurant);
        if (voteRepository.findByUserIdAndDate(authUser.getUser().id(),
                currentDate).isEmpty()) {
            return mapper.entityToVoteResponseTo(voteRepository.save(vote));
        } else {
            throw new IllegalRequestDataException("Vote already exists");
        }
    }

    @CacheEvict(value = "votes", key = "#userId")
    public void update(VoteTo voteTo, int userId) {
        checkDeadline(LocalTime.now());
        int restaurantId = voteTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException(ErrorType.BAD_REQUEST.title));
        Vote vote = voteRepository.findByUserIdAndDate(userId, LocalDate.now())
                .orElseThrow(() -> new IllegalRequestDataException("You " +
                        "should vote first"));
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }


    private void checkDeadline(LocalTime currentTime) {
        if (currentTime.isAfter(deadline))
            throw new IllegalRequestDataException("Impossible to change vote " +
                    "after " + deadline);
    }
}
