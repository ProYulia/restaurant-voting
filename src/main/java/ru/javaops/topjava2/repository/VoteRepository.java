package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query(value = "SELECT * FROM vote WHERE user_id =:userId AND date_effective =:date", nativeQuery = true)
    Optional<Vote> getByUserIdAndDate(int userId, LocalDate date);

    default Map<Integer, Integer> getTotal() {
        return countVotesByRestaurant().stream()
                .collect(Collectors.toMap(o -> (Integer) o[0], o -> (Integer) o[1]));
    };

    @Query(value = "SELECT restaurant_id, COUNT(id) FROM vote WHERE date_effective = current_date GROUP BY restaurant_id",
    nativeQuery = true)
    List<Object[]> countVotesByRestaurant();
}
