package com.store.project.controller;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.modelDTO.UserLoginDTO;
import com.store.project.util.Util;
import com.store.project.modelDTO.UserDTO;
import com.store.project.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import static com.store.project.util.Constants.*;

@RestController
@RequestMapping(USER_CONTROLLER_PATH)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = USER_REGISTER_PATH)
    public ResponseEntity<User> register(@RequestBody UserDTO user) {
        try {
            User registeredUser = userService.registerUser(Util.mapUserDTOToUser(user));
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (CustomExceptions.UserAlreadyExistsException e) {
            logger.error(String.format("User already exists: %s", user.getEmail()));
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (CustomExceptions.InvalidEmailException e) {
            logger.error(String.format("Invalid email format for user: %s", user.getEmail()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CustomExceptions.InvalidPasswordException e) {
            logger.error(String.format("Invalid password format for user: %s", user.getEmail()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(String.format("Unexpected error: %s", e.getMessage()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = USER_LOGIN_PATH)
    public ResponseEntity<User> login(@RequestBody UserLoginDTO user) {
        try {
            User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
            if (loggedInUser == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } catch (CustomExceptions.UserNotFoundException e) {
            logger.error(String.format("User not found with this email: %s", user.getEmail()));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CustomExceptions.InvalidPasswordException e) {
            logger.error("Password is invalid!");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Unexpected error during login: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
