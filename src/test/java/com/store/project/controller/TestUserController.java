package com.store.project.controller;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.service.UserService;
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
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("email@email.com");
        user.setPassword("Password12@");

        when(userService.registerUser(any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    public void testRegisterUserInvalidEmail() throws Exception {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("emailemail.com");
        user.setPassword("Password12@");

        doThrow(new CustomExceptions.InvalidEmailException("Invalid email format"))
                .when(userService).registerUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).registerUser(any(User.class));
    }

}
