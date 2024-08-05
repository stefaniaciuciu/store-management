package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
import com.store.project.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        var user = Util.createUserForTests();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepository.save(user);

        var updatedUser = userService.updatePassword(user.getEmail(), TEST_PASSWORD,
                CORRECT_NEW_PASSOWRD, CORRECT_NEW_PASSOWRD);

        assertNotNull(updatedUser);
        assertTrue(bcrypt.matches(CORRECT_NEW_PASSOWRD, updatedUser.getPassword()));

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailEmail() {
        var user = Util.createUserForTests();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepository.save(user);

        assertThrows(CustomExceptions.UserNotFoundException.class, () -> {
            userService.updatePassword(NON_EXISTING_EMAIL, TEST_PASSWORD,
                    CORRECT_NEW_PASSOWRD, CORRECT_NEW_PASSOWRD);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailWrongOldPassword() {
        var user = Util.createUserForTests();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), WRONG_OLD_PASSWORD,
                    CORRECT_NEW_PASSOWRD, CORRECT_NEW_PASSOWRD);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailInvalidNewPassword() {
        var user = Util.createUserForTests();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), TEST_PASSWORD,
                    INVALID_NEW_PASSWORD, INVALID_NEW_PASSWORD);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailDifferentNewPassword() {
        var user = Util.createUserForTests();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), TEST_PASSWORD,
                    CORRECT_NEW_PASSOWRD, WRONG_OLD_PASSWORD);
        });

        userRepository.delete(user);
    }

    @Test
    void testLoginOK() {
        var user = Util.createUserForTests();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        var userLoggedIn = userService.loginUser(user.getEmail(),TEST_PASSWORD);
        assertNotNull(userLoggedIn);
        assertEquals(user.getEmail(), userLoggedIn.getEmail());

        userRepository.delete(user);
    }

    @Test
    void testLoginFailUserNotFound() {
        var user = Util.createUserForTests();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        assertThrows(CustomExceptions.UserNotFoundException.class, () -> {
            userService.loginUser(NON_EXISTING_EMAIL, TEST_PASSWORD);
        });

        userRepository.delete(user);
    }

    @Test
    void testLoginFailWrongPassword() {
        var user = Util.createUserForTests();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.loginUser(user.getEmail(), CORRECT_NEW_PASSOWRD);
        });

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserOk() {
        var user = Util.createUserForTests();
        var registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        assertTrue(bcrypt.matches(TEST_PASSWORD, registeredUser.getPassword()));

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserFailUserAlreadyExists() {
        var user = Util.createUserForTests();
        userRepository.save(user);

        assertThrows(CustomExceptions.UserAlreadyExistsException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserFailInvalidEmail() {
        var user = Util.createUserForTests();
        user.setEmail(INVALID_EMAIL);

        assertThrows(CustomExceptions.InvalidEmailException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserFailInvalidPassword() {
        var user = Util.createUserForTests();
        user.setPassword(INVALID_NEW_PASSWORD);

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

}
