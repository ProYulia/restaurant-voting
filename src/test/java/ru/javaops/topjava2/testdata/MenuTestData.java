package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;

public class MenuTestData {
    public static final int MENU1_ID = 6;
    public static final int MENU2_ID = 8;
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");

    public static final Menu menu1 = new Menu(MENU1_ID, LocalDate.of(2023, 12, 23));
    public static final Menu menu2 = new Menu(MENU2_ID, LocalDate.now());

    public static Menu getNew() {
        return new Menu(LocalDate.now());
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, LocalDate.now().plusDays(1));
    }
}
