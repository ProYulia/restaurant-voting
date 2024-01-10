package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Menu;


import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Query(value = "SELECT * FROM menu WHERE date_created =:today", nativeQuery = true)
    List<Menu> findAllFilteredByDate(LocalDate today);
}
