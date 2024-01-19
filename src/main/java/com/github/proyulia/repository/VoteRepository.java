package com.github.proyulia.repository;

import com.github.proyulia.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query(value = "SELECT COUNT(v.id) FROM Vote v WHERE v.restaurant.id =: restaurantId")
    Integer countVotesByRestaurantId(int restaurantId);

    Optional<Vote> findByUserIdAndDate(Integer user_id, LocalDate date);
}
