package com.store.project.controller;

import com.store.project.model.User;
import com.store.project.modelDTO.UserLoginDTO;
import com.store.project.util.Util;
import com.store.project.modelDTO.UserDTO;
import com.store.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.store.project.util.Constants.*;

@RestController
@RequestMapping(USER_CONTROLLER_PATH)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = USER_REGISTER_PATH)
    public User register(@RequestBody UserDTO user) {
        try {
            return userService.registerUser(Util.mapUserDTOToUser(user));
        } catch (Exception e) {
            e.printStackTrace(); // more accurate exception needed!!!!!!
            return null;
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
