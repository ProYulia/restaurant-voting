package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;

public class MenuTestData {
    public static final int MENU_ID = 6;
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");

    public static Menu getNew() {
        return new Menu(LocalDate.now());
    }

    public static Menu getUpdated() {
        return new Menu(MENU_ID, LocalDate.now().plusDays(1));
    }
}
