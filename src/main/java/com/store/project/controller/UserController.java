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
    public User register(@RequestBody UserDTO user) {
        try {
            return userService.registerUser(Util.mapUserDTOToUser(user));
        } catch (CustomExceptions.UserAlreadyExistsException e) {
            logger.error(String.format("User already exists: %s", user.getEmail()));
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        } catch (CustomExceptions.InvalidEmailException e) {
            logger.error(String.format("Invalid email format for user: %s", user.getEmail()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        } catch (CustomExceptions.InvalidPasswordException e) {
            logger.error(String.format("Invalid password format for user: %s", user.getEmail()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password format");
        } catch (Exception e) {
            logger.error(String.format("Unexpected error: %s", e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error during registration", e);
        }
    }

    @GetMapping(value = USER_LOGIN_PATH)
    public User login(@RequestBody UserLoginDTO user) {
        try {
            return userService.loginUser(Util.mapUserLoginDTOToUser(user));
        } catch(Exception e) {
            e.printStackTrace(); //other exception
            return null;
        }
    }

}
