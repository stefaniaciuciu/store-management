package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
import static com.store.project.util.Constants.*;
import com.store.project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    Logger logger = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if(!Util.validatePassword(user.getPassword())) {
            logger.error(String.format(INVALID_PASSWORD_FORMAT, user.getEmail()));
            throw new CustomExceptions.InvalidPasswordException(String.format(INVALID_PASSWORD_FORMAT,
                    user.getEmail()));
        }
        if(!Util.validateEmail(user.getEmail())) {
            logger.error(String.format(INVALID_EMAIL_FORMAT, user.getEmail()));
            throw new CustomExceptions.InvalidEmailException(String.format(INVALID_EMAIL_FORMAT,
                    user.getEmail()));
        }
        boolean userExists = Optional.ofNullable(getUserByEmail(user.getEmail())).isPresent();
        if (userExists) {
            logger.error(USER_ALREADY_EXISTS);
            throw new CustomExceptions.UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        var existingUser = getUserByEmail(email);
        if (existingUser == null) {
            throw new CustomExceptions.UserNotFoundException(USER_NOT_FOUND);
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        if (!bcrypt.matches(password, existingUser.getPassword())) {
            throw new CustomExceptions.InvalidPasswordException(WRONG_PASSWORD);
        }
        return existingUser;
    }

    public User updatePassword(String email, String password, String newPassword, String confirmPassword) {
        User existingUser = getUserByEmail(email);
        if (existingUser == null) {
            logger.error(USER_NOT_FOUND);
            throw new CustomExceptions.UserNotFoundException(USER_NOT_FOUND);
        }
        if(!Util.validatePassword(newPassword)) {
            logger.error(String.format(INVALID_PASSWORD_FORMAT, existingUser.getEmail()));
            throw new CustomExceptions.InvalidPasswordException(String.format(INVALID_PASSWORD_FORMAT,
                    existingUser.getEmail()));
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        if (!bcrypt.matches(password, existingUser.getPassword())) {
            logger.error(WRONG_PASSWORD);
            throw new CustomExceptions.InvalidPasswordException(WRONG_PASSWORD);
        }
        if(!newPassword.equals(confirmPassword)) {
            logger.error(PASSWORDS_DO_NOT_MATCH);
            throw new CustomExceptions.InvalidPasswordException(PASSWORDS_DO_NOT_MATCH);
        }

        String encodedPassword = bcrypt.encode(newPassword);
        existingUser.setPassword(encodedPassword);
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