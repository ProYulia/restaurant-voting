package com.github.proyulia.testdata;

import com.github.proyulia.to.MenuTo;
import com.github.proyulia.web.MatcherFactory;

import java.time.LocalDate;

public class MenuTestData {
    public static final int MENU1_ID = 1;

    public static final int MENU2_ID = 3;

    public static final MatcherFactory.Matcher<MenuTo> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(MenuTo.class,
                    "restaurant", "dishes");

    public static final MenuTo menu1 = new MenuTo(MENU1_ID, LocalDate.of(2023
            , 12
            , 23));

    public static final MenuTo menu2 = new MenuTo(MENU2_ID, LocalDate.now());

    public static MenuTo getNew() {
        return new MenuTo(LocalDate.now());
    }

    public static MenuTo getUpdated() {
        return new MenuTo(MENU1_ID, LocalDate.now().plusDays(1));
    }
}
