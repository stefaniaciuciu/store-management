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
        try {
            Util.validatePassword(user.getPassword());
            Util.validateEmail(user.getEmail());
            boolean userExists = getUserByEmail(user.getEmail()) != null;

            if (userExists) {
                throw new CustomExceptions.UserAlreadyExistsException("User already exists");
            }

            return userRepository.save(user);
        } catch (CustomExceptions.InvalidPasswordException e) {
            logger.error("Invalid password for user: {}", user.getEmail(), e);
            throw e;
        } catch (CustomExceptions.InvalidEmailException e) {
            logger.error("Invalid email format for user: {}", user.getEmail(), e);
            throw e; // Optional: Custom handling or additional context can be added here
        } catch (CustomExceptions.UserAlreadyExistsException e) {
            logger.error("User already exists: {}", user.getEmail(), e);
            throw e; // Optional: Custom handling or additional context can be added here
        } catch (Exception e) {
            logger.error("Unexpected error during user registration for user: {}", user.getEmail(), e);
            throw new RuntimeException("Unexpected error during user registration", e);
        }
    }


    public User loginUser(User user) {
        try {
            User existingUser = getUserByEmail(user.getEmail());
            if (existingUser == null) {
                throw new CustomExceptions.UserNotFoundException("User with this email does not exist.");
            }
            if (user.getPassword().equals(existingUser.getPassword())) {
                throw new CustomExceptions.InvalidPasswordException("Invalid password.");
            }
            return existingUser;
        } catch (CustomExceptions.UserNotFoundException | CustomExceptions.InvalidPasswordException e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during login", e);
        }
    }

    public User getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch(Exception e) {
            throw new RuntimeException("User can not be found ", e);
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
