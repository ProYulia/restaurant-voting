package com.github.proyulia.testdata;

import com.github.proyulia.model.Restaurant;
import com.github.proyulia.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus");

    public static final int RESTAURANT1_ID = 1;

    public static final int RESTAURANT2_ID = 2;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Pizzeria");

    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Sushi");

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated_name");
    }
}
