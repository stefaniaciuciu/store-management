package com.store.project.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.project.controller.UserController;
import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Product;
import com.store.project.model.Purchase;
import com.store.project.model.User;
import com.store.project.modelDTO.ProductDTO;
import com.store.project.modelDTO.PurchaseDTO;
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

    public static Boolean validateEmail(String email) {
        String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (Pattern.matches(regexEmailPattern, email)) {
            return true;
        } else {
            throw new CustomExceptions.InvalidEmailException("Email is not in correct format!");
        }
    }

    public static void validatePassword(String password) {
        if (password == null) {
            throw new CustomExceptions.InvalidPasswordException("Password is empty!");
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        if (!Pattern.matches(regex, password)) {
            throw new CustomExceptions.InvalidPasswordException("Password is not in correct format!");
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

    public static Product mapProductDTOtoProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        return product;
    }

    public static Purchase mapPurchaseDTOtoPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setProductName(purchaseDTO.getProductName());
        purchase.setDate(purchaseDTO.getDate());
        purchase.setQuantity(purchaseDTO.getQuantity());

        return purchase;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
