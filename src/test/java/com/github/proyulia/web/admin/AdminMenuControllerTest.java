package com.github.proyulia.web.admin;

import com.github.proyulia.mapper.MenuMapper;
import com.github.proyulia.mapper.MenuMapperImpl;
import com.github.proyulia.repository.MenuRepository;
import com.github.proyulia.to.MenuTo;
import com.github.proyulia.util.JsonUtil;
import com.github.proyulia.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.proyulia.testdata.MenuTestData.*;
import static com.github.proyulia.testdata.UserTestData.ADMIN_MAIL;
import static com.github.proyulia.testdata.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuController.REST_URL;

    private static final int RESTAURANT_ID = 1;

    private static final int RESTAURANT2_ID = 3;

    private final MenuMapper mapper = new MenuMapperImpl();

    @Autowired
    private MenuRepository repository;

    @Test
    void createUnAuth() throws Exception {
        MenuTo newMenu = getNew();
        perform(MockMvcRequestBuilders
                .post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        MenuTo newMenu = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        MenuTo invalid = new MenuTo(null);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        MenuTo newMenu = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL,
                        RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated());

        MenuTo created = MENU_MATCHER.readFromJson(action);
        int newId = created.getId();
        MenuTo expected = getNew();
        expected.setId(newId);
        MENU_MATCHER.assertMatch(created, expected);
        MENU_MATCHER.assertMatch(mapper.toMenuTo(repository.getExisted(newId)), expected);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuTo updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/{id}", RESTAURANT_ID,
                        MENU1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(mapper.toMenuTo(repository.getExisted(MENU1_ID)), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/menus", RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1, menu2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/{id}", RESTAURANT_ID,
                MENU1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/{id}", RESTAURANT_ID,
                MENU2_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(MENU2_ID).isPresent());
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteHistoricalData() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/{id}", RESTAURANT_ID,
                MENU1_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
