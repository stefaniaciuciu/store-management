package com.store.project.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Util {

    public static Boolean validateEmail(String email) {
        String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexEmailPattern)
                .matcher(email)
                .matches();
    }

    public static Boolean validatePhoneNumber(String phoneNumber) {
        String regexPhoneNumberPattern = "^[0-9]{10}$";
        return Pattern.compile(regexPhoneNumberPattern)
                .matcher(phoneNumber)
                .matches();
    }

    public static Boolean validatePassword(String password) {
        String regexPasswordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=]).{8,}$";
        return Pattern.compile(regexPasswordPattern)
                .matcher(password)
                .matches();
    }
}
