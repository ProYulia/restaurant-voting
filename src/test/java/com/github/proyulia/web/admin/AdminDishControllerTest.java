package com.github.proyulia.web.admin;

import com.github.proyulia.mapper.DishMapper;
import com.github.proyulia.mapper.DishMapperImpl;
import com.github.proyulia.repository.DishRepository;
import com.github.proyulia.to.DishTo;
import com.github.proyulia.util.JsonUtil;
import com.github.proyulia.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.proyulia.testdata.DishTestData.*;
import static com.github.proyulia.testdata.MenuTestData.MENU1_ID;
import static com.github.proyulia.testdata.MenuTestData.MENU2_ID;
import static com.github.proyulia.testdata.UserTestData.ADMIN_MAIL;
import static com.github.proyulia.testdata.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL;

    private static final int RESTAURANT_ID = 1;

    private static final int MENU_ID = 1;

    private final DishMapper mapper = new DishMapperImpl();

    @Autowired
    private DishRepository repository;


    @Test
    void createUnAuth() throws Exception {
        DishTo newDish = getNew();
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
        DishTo newDish = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        DishTo invalid = new DishTo(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        DishTo newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL,
                        RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        DishTo created = DISH_MATCHER.readFromJson(action);
        int newId = created.getId();
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(mapper.toDishTo(repository.getExisted(newId)), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/{id}", RESTAURANT_ID,
                        MENU_ID, DISH_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(mapper.toDishTo(repository.getExisted(DISH_ID)), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/dishes",
                RESTAURANT_ID, MENU1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/{id}", RESTAURANT_ID,
                MENU1_ID, DISH_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/{id}", RESTAURANT_ID,
                MENU2_ID, DISH4_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(repository.findById(DISH4_ID).isPresent());
    }
}
