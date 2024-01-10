package ru.javaops.topjava2.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.testdata.DishTestData.DISH_MATCHER;
import static ru.javaops.topjava2.testdata.DishTestData.getNew;
import static ru.javaops.topjava2.testdata.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava2.testdata.UserTestData.USER_MAIL;

public class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL;
    private static final int RESTAURANT_ID = 4;
    private static final int MENU_ID = 6;

    @Autowired
    private DishRepository dishRepository;

    @Test
    void createUnAuth() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders
                .post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        Dish expected = getNew();
        expected.setId(newId);
        DISH_MATCHER.assertMatch(created, expected);
        DISH_MATCHER.assertMatch(dishRepository.getExisted(newId), expected);
    }
}
