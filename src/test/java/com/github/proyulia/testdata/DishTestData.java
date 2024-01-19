package com.github.proyulia.testdata;

import com.github.proyulia.to.DishTo;
import com.github.proyulia.web.MatcherFactory;

public class DishTestData {
    public static final int DISH_ID = 1;

    public static final int DISH4_ID = 4;

    public static final MatcherFactory.Matcher<DishTo> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(DishTo.class, "id");

    public static final DishTo dish1 = new DishTo(DISH_ID, "Cheese pizza", 299);

    public static DishTo getNew() {
        return new DishTo(null, "new dish", 300);
    }

    public static DishTo getUpdated() {
        return new DishTo(DISH_ID, "updated name", 199);
    }
}
