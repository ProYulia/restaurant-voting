package ru.javaops.topjava2.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Menu;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
}
