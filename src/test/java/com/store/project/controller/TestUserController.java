package com.store.project.controller;

import com.store.project.config.SecurityConfig;
import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.modelDTO.UserUpdateDTO;
import com.store.project.service.UserService;
import com.store.project.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.store.project.util.Constants.*;
import static com.store.project.util.Util.asJsonString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TestUserController {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void testRegisterUserOK() throws Exception {
        var user = Util.createUserForTests();

        when(userService.registerUser(any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PATH + USER_REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        var user = Util.createUserForTests();

        doThrow(new CustomExceptions.UserAlreadyExistsException(USER_ALREADY_EXISTS))
                .when(userService).registerUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PATH + USER_REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testRegisterUserInvalidEmail() throws Exception {
        var user = Util.createUserForTests();

        doThrow(new CustomExceptions.InvalidEmailException(INVALID_EMAIL_FORMAT))
                .when(userService).registerUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PATH + USER_REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testRegisterUserInvalidPassword() throws Exception {
        var user = Util.createUserForTests();

        doThrow(new CustomExceptions.InvalidPasswordException(INVALID_PASSWORD_FORMAT))
                .when(userService).registerUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PATH + USER_REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testUpdatePasswordOK() throws Exception {
        var user = Util.createUserForTests();
        String newPassword = "newPassword";
        String userUpdateDTOJson = String.format(
                "{\"email\":\"%s\",\"oldPassword\":\"%s\",\"newPassword\":\"%s\",\"confirmPassword\":\"%s\"}",
                user.getEmail(), user.getPassword(), newPassword, newPassword
        );

        when(userService.updatePassword(user.getEmail(),user.getPassword(), newPassword, newPassword))
                .thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.patch(USER_CONTROLLER_PATH + USER_UPDATE_PASSWORD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDTOJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).updatePassword(user.getEmail(),user.getPassword(),
                newPassword, newPassword);
    }

    @Test
    public void testUpdatePasswordFailUserNotFound() throws Exception {
        var user = Util.createUserForTests();
        String newPassword = "newPassword";
        String userUpdateDTOJson = String.format(
                "{\"email\":\"%s\",\"oldPassword\":\"%s\",\"newPassword\":\"%s\",\"confirmPassword\":\"%s\"}",
                user.getEmail(), user.getPassword(), newPassword, newPassword
        );

        doThrow(new CustomExceptions.UserNotFoundException(USER_NOT_FOUND))
                .when(userService).updatePassword(user.getEmail(),user.getPassword(), newPassword, newPassword);

        mockMvc.perform(MockMvcRequestBuilders.patch(USER_CONTROLLER_PATH + USER_UPDATE_PASSWORD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDTOJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).updatePassword(user.getEmail(),user.getPassword(),
                newPassword, newPassword);
    }

    @Test
    public void testUpdatePasswordFailIvalidPassword() throws Exception {
        var user = Util.createUserForTests();
        String newPassword = "newPassword";
        String userUpdateDTOJson = String.format(
                "{\"email\":\"%s\",\"oldPassword\":\"%s\",\"newPassword\":\"%s\",\"confirmPassword\":\"%s\"}",
                user.getEmail(), user.getPassword(), newPassword, newPassword
        );

        doThrow(new CustomExceptions.InvalidPasswordException(USER_NOT_FOUND))
                .when(userService).updatePassword(user.getEmail(),user.getPassword(), newPassword, newPassword);

        mockMvc.perform(MockMvcRequestBuilders.patch(USER_CONTROLLER_PATH + USER_UPDATE_PASSWORD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDTOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).updatePassword(user.getEmail(),user.getPassword(),
                newPassword, newPassword);
    }

    @Test
    public void testLoginOk() throws Exception {
        var user = Util.createUserForTests();
        String userLoginDTOJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                user.getEmail(), user.getPassword()
        );

        when(userService.loginUser(user.getEmail(),user.getPassword()))
                .thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get(USER_CONTROLLER_PATH + USER_LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).loginUser(user.getEmail(),user.getPassword());
    }

    @Test
    public void testLoginFailUserNotFound() throws Exception {
        var user = Util.createUserForTests();
        String userLoginDTOJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                user.getEmail(), user.getPassword()
        );

        doThrow(new CustomExceptions.UserNotFoundException(USER_NOT_FOUND))
                .when(userService).loginUser(user.getEmail(),user.getPassword());

        mockMvc.perform(MockMvcRequestBuilders.get(USER_CONTROLLER_PATH + USER_LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).loginUser(user.getEmail(),user.getPassword());
    }

    @Test
    public void testLoginFailInvalidPassword() throws Exception {
        var user = Util.createUserForTests();
        String userLoginDTOJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                user.getEmail(), user.getPassword()
        );

        doThrow(new CustomExceptions.InvalidPasswordException(WRONG_PASSWORD))
                .when(userService).loginUser(user.getEmail(),user.getPassword());

        mockMvc.perform(MockMvcRequestBuilders.get(USER_CONTROLLER_PATH + USER_LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verify(userService, times(1)).loginUser(user.getEmail(),user.getPassword());
    }

}
