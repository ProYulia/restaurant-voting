package com.github.proyulia.repository;

import com.github.proyulia.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query(value = "SELECT * FROM vote WHERE user_id =:userId AND date_created =:date", nativeQuery = true)
    Optional<Vote> getByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDate date);

    default Map<Integer, Integer> getTotal() {
        return countVotesByRestaurant().stream()
                .collect(Collectors.toMap(o -> (Integer) o[0], o -> ((Long) o[1]).intValue()));
    }

    @Query(value = "SELECT restaurant_id, COUNT(id) FROM vote WHERE date_created = current_date GROUP BY restaurant_id",
            nativeQuery = true)
    List<Object[]> countVotesByRestaurant();


    @Query(value = "SELECT COUNT(id) FROM vote WHERE restaurant_id =: restaurantId",
            nativeQuery = true)
    Integer countVotesByRestaurantId(int restaurantId);
}
