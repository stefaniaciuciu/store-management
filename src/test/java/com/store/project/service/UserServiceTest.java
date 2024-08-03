package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
import com.store.project.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUpdatePasswordOk() {
        User user = Util.createUserForTests();
        userRepository.save(user);
        String correctPassword = "newPassword12&";

        User updatedUser = userService.updatePassword(user.getEmail(), user.getPassword(),
                correctPassword, correctPassword);

        assertNotNull(updatedUser);
        assertEquals(correctPassword, updatedUser.getPassword());

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailEmail() {
        User user = Util.createUserForTests();
        userRepository.save(user);
        String nonExistingEmail = "nonexistingemail@gmail.com";
        String correctNewPassword = "newPassword12&";

        assertThrows(CustomExceptions.UserNotFoundException.class, () -> {
            userService.updatePassword(nonExistingEmail, user.getPassword(),
                    correctNewPassword, correctNewPassword);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailWrongOldPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);
        String wrongOldPassword = "wrongPassword";
        String correctNewPassword = "newPassword12&";

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), wrongOldPassword,
                    correctNewPassword, correctNewPassword);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailInvalidNewPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);
        String correctOldPassword = "oldPassword";
        String invalidNewPassword = "newPassword";

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), correctOldPassword,
                    invalidNewPassword, invalidNewPassword);
        });

        userRepository.delete(user);
    }

    @Test
    void testUpdatePasswordFailDifferentNewPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);
        String correctOldPassword = "oldPassword";
        String correctNewPassword = "newPassword12&";
        String differentNewPassword = "wrongPassword";

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), correctOldPassword,
                    correctNewPassword, differentNewPassword);
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
        String nonExistingEmail = "nonexistingemail@gmail.com";

        assertThrows(CustomExceptions.UserNotFoundException.class, () -> {
            userService.loginUser(nonExistingEmail, user.getPassword());
        });

        userRepository.delete(user);
    }

    @Test
    void testLoginFailWrongPassword() {
        User user = Util.createUserForTests();
        userRepository.save(user);
        String wrongPassword = "wrongPassword";

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.loginUser(user.getEmail(), wrongPassword);
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
        user.setEmail("invalidEmail.com");

        assertThrows(CustomExceptions.InvalidEmailException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

    @Test
    void testRegisterUserFailInvalidPassword() {
        User user = Util.createUserForTests();
        user.setPassword("Password");

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.registerUser(user);
        });

        userRepository.delete(user);
    }

}
