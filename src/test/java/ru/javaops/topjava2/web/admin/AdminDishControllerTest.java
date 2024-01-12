package ru.javaops.topjava2.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.testdata.DishTestData;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.testdata.DishTestData.DISH_ID;
import static ru.javaops.topjava2.testdata.DishTestData.DISH_MATCHER;
import static ru.javaops.topjava2.testdata.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava2.testdata.UserTestData.USER_MAIL;

public class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL;
    private static final int RESTAURANT_ID = 4;
    private static final int MENU_ID = 6;

    @Autowired
    private DishRepository repository;

    @Test
    void createUnAuth() throws Exception {
        Dish newDish = DishTestData.getNew();
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
        Dish newDish = DishTestData.getNew();
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
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        Dish expected = DishTestData.getNew();
        expected.setId(newId);
        DISH_MATCHER.assertMatch(created, expected);
        DISH_MATCHER.assertMatch(repository.getExisted(newId), expected);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL +"/{id}", RESTAURANT_ID, MENU_ID, DISH_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(repository.getExisted(DISH_ID), DishTestData.getUpdated());
    }
}
