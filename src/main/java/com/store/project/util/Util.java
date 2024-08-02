package com.store.project.util;

import com.store.project.controller.UserController;
import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.modelDTO.UserDTO;
import com.store.project.modelDTO.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Component
public class Util {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static Boolean validateEmail(String email) {
        try {
            String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if (Pattern.compile(regexEmailPattern).matcher(email).matches()) {
                return true;
            } else {
                throw new CustomExceptions.InvalidEmailException("Email is not in correct format");
            }
        } catch (PatternSyntaxException e) {
            throw new IllegalStateException("Invalid email pattern", e);
        }
    }

//    public static Boolean validatePhoneNumber(String phoneNumber) {
//        try {
//            String regexPhoneNumberPattern = "^[0-9]{10}$";
//            if (Pattern.compile(regexPhoneNumberPattern).matcher(phoneNumber).matches()) {
//                return true;
//            } else {
//                throw new CustomExceptions.InvalidPhoneNumberException("Phone number is not in correct format");
//            }
//        } catch (PatternSyntaxException e) {
//            throw new IllegalStateException("Invalid phone number pattern", e);
//        }
//    }

    public static boolean validatePassword(String password) {
            try {
                String regexPasswordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=]).{8,}$";
                if (Pattern.compile(regexPasswordPattern).matcher(password).matches()) {
                    return true;
                } else {
                    throw new CustomExceptions.InvalidPasswordException("Password is not in correct format");
                }
            } catch (PatternSyntaxException e) {
                throw new IllegalStateException("Invalid password pattern", e);
            }
    }

    public static User mapUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public static User mapUserLoginDTOToUser(UserLoginDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }
}
