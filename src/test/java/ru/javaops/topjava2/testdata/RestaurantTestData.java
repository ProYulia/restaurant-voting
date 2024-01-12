package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantResponseTo;
import ru.javaops.topjava2.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus");
    public static final int RESTAURANT1_ID = 4;
    public static final int RESTAURANT2_ID = 5;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Pizzeria");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Sushi");
    public static final RestaurantResponseTo restaurant_3 = new RestaurantResponseTo();

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated_name");
    }
}
