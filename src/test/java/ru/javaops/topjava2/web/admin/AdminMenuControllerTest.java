package ru.javaops.topjava2.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.testdata.MenuTestData.MENU_MATCHER;
import static ru.javaops.topjava2.testdata.MenuTestData.getNew;
import static ru.javaops.topjava2.testdata.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava2.testdata.UserTestData.USER_MAIL;

public class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuController.REST_URL;
    private static final int RESTAURANT_ID = 4;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void createUnAuth() throws Exception {
        Menu newMenu = getNew();
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
        Menu newMenu = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Menu invalid = new Menu(null);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Menu newMenu = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        Menu expected = getNew();
        expected.setId(newId);
        MENU_MATCHER.assertMatch(created, expected);
        MENU_MATCHER.assertMatch(menuRepository.getExisted(newId), expected);
    }
}
