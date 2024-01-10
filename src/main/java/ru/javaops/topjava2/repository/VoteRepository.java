package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query(value = "SELECT * FROM vote WHERE user_id =:userId AND date_effective =:date", nativeQuery = true)
    Optional<Vote> getByUserIdAndDate(int userId, LocalDate date);
}
