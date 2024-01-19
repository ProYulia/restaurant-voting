package com.github.proyulia.repository;

import com.github.proyulia.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    default Map<Integer, Integer> getTotal() {
        return countVotesByRestaurant().stream()
                .collect(Collectors.toMap(o -> (Integer) o[0], o -> ((Long) o[1]).intValue()));
    }

    @Query(value = "SELECT v.restaurant.id, COUNT(v.id) FROM Vote v WHERE v.date = current_date GROUP BY v.restaurant.id")
    List<Object[]> countVotesByRestaurant();


    @Query(value = "SELECT COUNT(v.id) FROM Vote v WHERE v.restaurant.id =: restaurantId")
    Integer countVotesByRestaurantId(int restaurantId);

    Optional<Vote> findByUserIdAndDate(Integer user_id, LocalDate date);
}
