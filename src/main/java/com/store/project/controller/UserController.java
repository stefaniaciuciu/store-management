package com.store.project.controller;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.modelDTO.UserLoginDTO;
import com.store.project.modelDTO.UserUpdateDTO;
import com.store.project.util.Util;
import com.store.project.modelDTO.UserDTO;
import com.store.project.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.store.project.util.Constants.*;

@RestController
@RequestMapping(USER_CONTROLLER_PATH)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = USER_REGISTER_PATH)
    public ResponseEntity<User> register(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.registerUser(Util.mapUserDTOToUser(user)));
    }

    @PatchMapping(value = USER_UPDATE_PASSWORD)
    public ResponseEntity<User> updatePassword(@RequestBody UserUpdateDTO user) {
        return ResponseEntity.ok(userService.updatePassword(user.getEmail(), user.getOldPassword(),
                user.getNewPassword(), user.getConfirmPassword()));
    }

    @PostMapping(value = USER_LOGIN_PATH)
    public ResponseEntity<User> login(@RequestBody UserLoginDTO user) {
            User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(loggedInUser);
    }

}
