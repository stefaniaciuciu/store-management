package com.store.project.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.project.model.Product;
import com.store.project.model.Purchase;
import com.store.project.model.User;
import com.store.project.modelDTO.ProductDTO;
import com.store.project.modelDTO.PurchaseDTO;
import com.store.project.modelDTO.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.store.project.util.Constants.*;

@Component
public class Util {

    public static Boolean validateEmail(String email) {
        String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(regexEmailPattern, email);
    }

    public static Boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        return Pattern.matches(regex, password);
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

    public static User createUserForTests() {
        User user = new User();

        user.setFirstName(TEST_USER_NAME);
        user.setLastName(TEST_USER_NAME);
        user.setEmail(TEST_EMAIL_ADDRESS);
        user.setPassword(TEST_PASSWORD);
        user.setUsername(TEST_EMAIL_ADDRESS);

        return user;
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
