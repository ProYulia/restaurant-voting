package com.github.proyulia.web.admin;

import com.github.proyulia.model.Role;
import com.github.proyulia.model.User;
import com.github.proyulia.repository.UserRepository;
import com.github.proyulia.testdata.UserTestData;
import com.github.proyulia.web.AbstractControllerTest;
import com.github.proyulia.web.UniqueMailValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = AdminUserController.REST_URL + '/';

    @Autowired
    private UserRepository repository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void enableNotFound() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + UserTestData.NOT_FOUND)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminUserController.REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminUserController.REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        User updated = UserTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(updated, "newPass")))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserTestData.USER_MATCHER.assertMatch(repository.getExisted(UserTestData.USER_ID), UserTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createWithLocation() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminUserController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(newUser, "newPass")))
                .andExpect(status().isCreated());

        User created = UserTestData.USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(repository.getExisted(newId), newUser);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminUserController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.admin, UserTestData.guest, UserTestData.user));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void enable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + UserTestData.USER_ID)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(repository.getExisted(UserTestData.USER_ID).isEnabled());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createInvalid() throws Exception {
        User invalid = new User(null, null, "", "newPass", Role.USER, Role.ADMIN);
        perform(MockMvcRequestBuilders.post(AdminUserController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(invalid, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateInvalid() throws Exception {
        User invalid = new User(UserTestData.user);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(invalid, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        User updated = new User(UserTestData.user);
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        User updated = new User(UserTestData.user);
        updated.setEmail(UserTestData.ADMIN_MAIL);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL)));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createDuplicate() throws Exception {
        User expected = new User(null, "New", UserTestData.USER_MAIL, "newPass", Role.USER, Role.ADMIN);
        perform(MockMvcRequestBuilders.post(AdminUserController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(expected, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL)));
    }
}