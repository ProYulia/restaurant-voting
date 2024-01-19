package com.github.proyulia.testdata;

import com.github.proyulia.to.RestaurantTo;
import com.github.proyulia.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class,
                    "menus");

    public static final int RESTAURANT1_ID = 1;

    public static final int RESTAURANT2_ID = 2;

    public static final int RESTAURANT3_ID = 3;

    public static final RestaurantTo restaurant1 =
            new RestaurantTo(RESTAURANT1_ID, "Pizzeria");

    public static final RestaurantTo restaurant2 =
            new RestaurantTo(RESTAURANT2_ID, "Sushi");

    public static final RestaurantTo restaurant3 =
            new RestaurantTo(RESTAURANT3_ID, "Chinese");

    public static RestaurantTo getNew() {
        return new RestaurantTo(null, "New");
    }

    public static RestaurantTo getUpdated() {
        return new RestaurantTo(RESTAURANT1_ID, "Updated_name");
    }
}
