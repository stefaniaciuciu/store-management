package com.store.project.controller;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.modelDTO.UserUpdateDTO;
import com.store.project.service.UserService;
import com.store.project.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.store.project.util.Util.asJsonString;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class TestUserController {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testRegisterUserOK() throws Exception {
        User user = Util.createUserForTests();

        when(userService.registerUser(any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        User user = Util.createUserForTests();

        doThrow(new CustomExceptions.UserAlreadyExistsException(String.format("User already exists: %s", user.getEmail())))
                .when(userService).registerUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testRegisterUserInvalidEmail() throws Exception {
        User user = Util.createUserForTests();

        doThrow(new CustomExceptions.InvalidEmailException("Invalid email format"))
                .when(userService).registerUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testRegisterUserInvalidPassword() throws Exception {
        User user = Util.createUserForTests();

        doThrow(new CustomExceptions.InvalidPasswordException("Invalid password format"))
                .when(userService).registerUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testUpdatePasswordOK() throws Exception {
        User user = Util.createUserForTests();
        String newPassword = "newPassword";
        String userUpdateDTOJson = String.format(
                "{\"email\":\"%s\",\"oldPassword\":\"%s\",\"newPassword\":\"%s\",\"confirmPassword\":\"%s\"}",
                user.getEmail(), user.getPassword(), newPassword, newPassword
        );

        when(userService.updatePassword(user.getEmail(),user.getPassword(), newPassword, newPassword))
                .thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.patch("/user/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDTOJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).updatePassword(user.getEmail(),user.getPassword(),
                newPassword, newPassword);
    }

    @Test
    public void testUpdatePasswordFailUserNotFound() throws Exception {
        User user = Util.createUserForTests();
        String newPassword = "newPassword";
        String userUpdateDTOJson = String.format(
                "{\"email\":\"%s\",\"oldPassword\":\"%s\",\"newPassword\":\"%s\",\"confirmPassword\":\"%s\"}",
                user.getEmail(), user.getPassword(), newPassword, newPassword
        );

        doThrow(new CustomExceptions.UserNotFoundException("User not found"))
                .when(userService).updatePassword(user.getEmail(),user.getPassword(), newPassword, newPassword);

        mockMvc.perform(MockMvcRequestBuilders.patch("/user/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDTOJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).updatePassword(user.getEmail(),user.getPassword(),
                newPassword, newPassword);
    }

    @Test
    public void testUpdatePasswordFailIvalidPassword() throws Exception {
        User user = Util.createUserForTests();
        String newPassword = "newPassword";
        String userUpdateDTOJson = String.format(
                "{\"email\":\"%s\",\"oldPassword\":\"%s\",\"newPassword\":\"%s\",\"confirmPassword\":\"%s\"}",
                user.getEmail(), user.getPassword(), newPassword, newPassword
        );

        doThrow(new CustomExceptions.InvalidPasswordException("User not found"))
                .when(userService).updatePassword(user.getEmail(),user.getPassword(), newPassword, newPassword);

        mockMvc.perform(MockMvcRequestBuilders.patch("/user/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDTOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).updatePassword(user.getEmail(),user.getPassword(),
                newPassword, newPassword);
    }

    @Test
    public void testLoginOk() throws Exception {
        User user = Util.createUserForTests();
        String userLoginDTOJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                user.getEmail(), user.getPassword()
        );

        when(userService.loginUser(user.getEmail(),user.getPassword()))
                .thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).loginUser(user.getEmail(),user.getPassword());
    }

    @Test
    public void testLoginFailUserNotFound() throws Exception {
        User user = Util.createUserForTests();
        String userLoginDTOJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                user.getEmail(), user.getPassword()
        );

        doThrow(new CustomExceptions.UserNotFoundException("User not found"))
                .when(userService).loginUser(user.getEmail(),user.getPassword());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).loginUser(user.getEmail(),user.getPassword());
    }

    @Test
    public void testLoginFailInvalidPassword() throws Exception {
        User user = Util.createUserForTests();
        String userLoginDTOJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                user.getEmail(), user.getPassword()
        );

        doThrow(new CustomExceptions.InvalidPasswordException("Password is invalid!"))
                .when(userService).loginUser(user.getEmail(),user.getPassword());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verify(userService, times(1)).loginUser(user.getEmail(),user.getPassword());
    }

}
