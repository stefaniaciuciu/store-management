package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
import com.store.project.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.store.project.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUpdatePasswordOk() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        User updatedUser = userService.updatePassword(user.getEmail(), user.getPassword(),
                CORRECT_NEW_PASSOWRD, CORRECT_NEW_PASSOWRD);

        assertNotNull(updatedUser);
        assertEquals(CORRECT_NEW_PASSOWRD, updatedUser.getPassword());

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailEmail() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.UserNotFoundException.class, () -> {
            userService.updatePassword(NON_EXISTING_EMAIL, user.getPassword(),
                    CORRECT_NEW_PASSOWRD, CORRECT_NEW_PASSOWRD);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailWrongOldPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), WRONG_OLD_PASSWORD,
                    CORRECT_NEW_PASSOWRD, CORRECT_NEW_PASSOWRD);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailInvalidNewPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), TEST_PASSWORD,
                    INVALID_NEW_PASSWORD, INVALID_NEW_PASSWORD);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailDifferentNewPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), TEST_PASSWORD,
                    CORRECT_NEW_PASSOWRD, WRONG_OLD_PASSWORD);
        });

        userRepository.delete(user);
    }

    @Test
    void testLoginOK() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        User userLoggedIn = userService.loginUser(user.getEmail(), user.getPassword());
        assertNotNull(userLoggedIn);
        assertEquals(user.getEmail(), userLoggedIn.getEmail());

        userRepository.delete(user);
    }

    @Test
    void testLoginFailUserNotFound() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.UserNotFoundException.class, () -> {
            userService.loginUser(NON_EXISTING_EMAIL, user.getPassword());
        });

        userRepository.delete(user);
    }

    @Test
    void testLoginFailWrongPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.loginUser(user.getEmail(), CORRECT_NEW_PASSOWRD);
        });

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserOk() {
        User user = Util.createUserForTests();
        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(user.getEmail(), registeredUser.getEmail());

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserFailUserAlreadyExists() {
        User user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.UserAlreadyExistsException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserFailInvalidEmail() {
        User user = Util.createUserForTests();
        user.setEmail(INVALID_EMAIL);

        assertThrows(CustomExceptions.InvalidEmailException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserFailInvalidPassword() {
        User user = Util.createUserForTests();
        user.setPassword(INVALID_NEW_PASSWORD);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

}
