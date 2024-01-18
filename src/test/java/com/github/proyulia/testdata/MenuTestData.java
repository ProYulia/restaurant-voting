package com.github.proyulia.testdata;

import com.github.proyulia.model.Menu;
import com.github.proyulia.web.MatcherFactory;

import java.time.LocalDate;

public class MenuTestData {
    public static final int MENU1_ID = 1;

    public static final int MENU2_ID = 3;

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