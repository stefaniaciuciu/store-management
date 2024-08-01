package com.store.project.controller;

import com.store.project.modelDTO.UserDTO;
import org.springframework.web.bind.annotation.*;

import static com.store.project.util.Constants.USER_REGISTER_PATH;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping(USER_REGISTER_PATH)
    public String registerUser(@RequestBody UserDTO user) {
        System.out.println(user);
        return user.getEmail();
    }

}
