package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
import com.store.project.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void updatePasswordOk() {
        User user = Util.createUserForTests("testfromcode2@example.com");
        userRepository.save(user);
        String correctPassword = "newPassword12&";

        User updatedUser = userService.updatePassword(user.getEmail(), user.getPassword(),
                correctPassword, correctPassword);

        assertNotNull(updatedUser);
        assertEquals(correctPassword, updatedUser.getPassword());
    }

    @Test
    void updatePasswordFail() {
        User user = Util.createUserForTests("testFail@gmail.com");
        userRepository.save(user);
        String wrongOldPassword = "wrongPassword";
        String correctNewPassword = "newPassword12&";

        assertThrows(CustomExceptions.InvalidPasswordException.class, () -> {
            userService.updatePassword(user.getEmail(), wrongOldPassword,
                    correctNewPassword, correctNewPassword);
        });
    }

}
