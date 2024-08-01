package com.store.project.util;

import com.store.project.model.User;
import com.store.project.modelDTO.UserDTO;
import com.store.project.modelDTO.UserLoginDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Component
public class Util {

    public static Boolean validateEmail(String email) {
        try {
            String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
            return Pattern.compile(regexEmailPattern)
                          .matcher(email)
                          .matches();
        } catch(PatternSyntaxException e) {
            throw new IllegalStateException("Email is not correct format", e);
        }
    }

    public static Boolean validatePhoneNumber(String phoneNumber) {
        try {
            String regexPhoneNumberPattern = "^[0-9]{10}$";
            return Pattern.compile(regexPhoneNumberPattern)
                    .matcher(phoneNumber)
                    .matches();
        } catch(PatternSyntaxException e) {
            throw new IllegalStateException("Phone number is not correct format", e);
        }
    }

    public static Boolean validatePassword(String password) {
        try {
            String regexPasswordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=]).{8,}$";
            return Pattern.compile(regexPasswordPattern)
                    .matcher(password)
                    .matches();
        } catch (PatternSyntaxException e) {
            throw new IllegalStateException("Password is not correct format, it should include: at least one special " +
                    "character, at least one upper case letter and minimum 8 characters", e);
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
