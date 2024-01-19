package com.github.proyulia.testdata;

import com.github.proyulia.model.Dish;
import com.github.proyulia.web.MatcherFactory;

public class DishTestData {
    public static final int DISH_ID = 1;

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static final Dish dish1 = new Dish(DISH_ID, "Cheeze pizza", 299);

    public static Dish getNew() {
        return new Dish(null, "new dish", 300);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "updated name", 199);
    }
}
