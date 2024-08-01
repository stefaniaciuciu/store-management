package com.store.project.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.store.project.Util.Constants.USER_CONTROLLER_PATH;
import static com.store.project.Util.Constants.USER_REGISTER_PATH;

@RestController
@RequestMapping(USER_CONTROLLER_PATH)
public class UserController {

    @PostMapping(USER_REGISTER_PATH)
    public String registerUser(@RequestBody User user) {
        return "TEST security";
    }

}
