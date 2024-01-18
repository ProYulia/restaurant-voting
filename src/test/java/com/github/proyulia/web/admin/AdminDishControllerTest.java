package com.github.proyulia.web.admin;

import com.github.proyulia.model.Dish;
import com.github.proyulia.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.proyulia.repository.DishRepository;
import com.github.proyulia.testdata.DishTestData;
import com.github.proyulia.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.proyulia.testdata.DishTestData.*;
import static com.github.proyulia.testdata.MenuTestData.MENU1_ID;
import static com.github.proyulia.testdata.UserTestData.ADMIN_MAIL;
import static com.github.proyulia.testdata.UserTestData.USER_MAIL;

public class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL;

    private static final int RESTAURANT_ID = 1;

    private static final int MENU_ID = 1;

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
        perform(MockMvcRequestBuilders.put(REST_URL + "/{id}", RESTAURANT_ID, MENU_ID, DISH_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(repository.getExisted(DISH_ID), DishTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/dishes", RESTAURANT_ID, MENU1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1)));
    }
}
