package com.store.project.service;

import com.store.project.controller.UserController;
import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
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
            throw new CustomExceptions.InvalidPasswordException(String.format("Invalid password format for user: %s",
                    user.getEmail()));
        }
        if(!Util.validateEmail(user.getEmail())) {
            throw new CustomExceptions.InvalidEmailException(String.format("Invalid email format for user: %s",
                    user.getEmail()));
        }
        boolean userExists = Optional.ofNullable(getUserByEmail(user.getEmail())).isPresent();
        if (userExists) {
            throw new CustomExceptions.UserAlreadyExistsException("User already exists");
        }

        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User existingUser = getUserByEmail(email);
        if (existingUser == null) {
            throw new CustomExceptions.UserNotFoundException("User with this email does not exist.");
        }
        if (!password.equals(existingUser.getPassword())) {
            throw new CustomExceptions.InvalidPasswordException("Invalid password.");
        }
        return existingUser;
    }

    public User updatePassword(String email, String password, String newPassword, String confirmPassword) {
        User existingUser = getUserByEmail(email);
        if (existingUser == null) {
            throw new CustomExceptions.UserNotFoundException("User with this email does not exist.");
        }
        if(!Util.validatePassword(newPassword)) {
            throw new CustomExceptions.InvalidPasswordException("Invalid password.");
        }
        if (!password.equals(existingUser.getPassword())) {
            throw new CustomExceptions.InvalidPasswordException("Passwords do not match.");
        }
        if(!newPassword.equals(confirmPassword)) {
            throw new CustomExceptions.InvalidPasswordException("Passwords do not match.");
        }
        existingUser.setPassword(newPassword);
        return userRepository.save(existingUser);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        return Optional.of(userRepository.findById(id))
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("User can not be found"));
    }

}
