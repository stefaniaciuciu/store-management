package com.store.project.service;

import com.store.project.controller.UserController;
import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
import static com.store.project.util.Constants.*;
import com.store.project.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if(!Util.validatePassword(user.getPassword())) {
            throw new CustomExceptions.InvalidPasswordException(String.format(INVALID_PASSWORD_FORMAT,
                    user.getEmail()));
        }
        if(!Util.validateEmail(user.getEmail())) {
            throw new CustomExceptions.InvalidEmailException(String.format(INVALID_EMAIL_FORMAT,
                    user.getEmail()));
        }
        boolean userExists = Optional.ofNullable(getUserByEmail(user.getEmail())).isPresent();
        if (userExists) {
            throw new CustomExceptions.UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }

        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User existingUser = getUserByEmail(email);
        if (existingUser == null) {
            throw new CustomExceptions.UserNotFoundException(USER_NOT_FOUND);
        }
        if (!password.equals(existingUser.getPassword())) {
            throw new CustomExceptions.InvalidPasswordException(String.format(INVALID_PASSWORD_FORMAT,
                    existingUser.getEmail()));
        }
        return existingUser;
    }

    public User updatePassword(String email, String password, String newPassword, String confirmPassword) {
        User existingUser = getUserByEmail(email);
        if (existingUser == null) {
            throw new CustomExceptions.UserNotFoundException(USER_NOT_FOUND);
        }
        if(!Util.validatePassword(newPassword)) {
            throw new CustomExceptions.InvalidPasswordException(INVALID_PASSWORD_FORMAT);
        }
        if (!password.equals(existingUser.getPassword())) {
            throw new CustomExceptions.InvalidPasswordException(WRONG_PASSWORD);
        }
        if(!newPassword.equals(confirmPassword)) {
            throw new CustomExceptions.InvalidPasswordException(PASSWORDS_DO_NOT_MATCH);
        }
        existingUser.setPassword(newPassword);
        return userRepository.save(existingUser);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        return Optional.of(userRepository.findById(id))
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException(USER_NOT_FOUND));
    }

}
