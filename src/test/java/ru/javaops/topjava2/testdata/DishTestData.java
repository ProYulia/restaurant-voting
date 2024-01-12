package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

public class DishTestData {
    public static final int DISH_ID = 10;
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static Dish getNew() {
        return new Dish(null, "new dish", 300);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "updated name", 199);
    }
}
