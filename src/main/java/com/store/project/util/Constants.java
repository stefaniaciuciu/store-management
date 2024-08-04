package com.store.project.util;

public class Constants {

    // Paths for User Controller
    public static final String USER_CONTROLLER_PATH = "/user";
    public static final String USER_REGISTER_PATH = "/register";
    public static final String USER_LOGIN_PATH = "/login";
    public static final String USER_UPDATE_PASSWORD = "/updatePassword";

    // Paths for Product Controller
    public static final String PRODUCT_CONTROLLER_PATH = "/product";
    public static final String ADD_NEW_PRODUCT = "/addNewProduct";
    public static final String UPDATE_PRODUCT = "/updateProduct/{id}";
    public static final String UPDATE_PRODUCT_PRICE = "/updateProduct/price/{price}/{id}";
    public static final String SHOW_PRODUCT_BY_ID = "/showProduct/{id}";
    public static final String DELETE_PRODUCT = "/deleteProduct/{id}";

    // Paths for Purchase Controller
    public static final String PURCHASE_CONTROLLER_PATH = "/purchase";
    public static final String GET_ALL_PURCHASES = "/getAllPurchases";
    public static final String GET_PURCHASE_BY_ID = "/getPurchase/{id}";
    public static final String ADD_PURCHASE = "/addPurchase/{userId}/{productId}";
    public static final String VIEW_PURCHASES_BY_USER = "/viewAllPurchasesByUser/{userId}";
    public static final String VIEW_PURCHASES_OF_PRODUCT = "/viewAllPurchasesOfProduct/{productId}";

    // Exceptions messages
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PURCHASE_NOT_FOUND = "Purchase not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String INVALID_PASSWORD_FORMAT = "Invalid password format for user: %s";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format for user: %s";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String WRONG_PASSWORD = "Wrong password!";
    public static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match!";

    // Constants for tests
    public static final String TEST_USER_NAME = "Test";
    public static final String TEST_PASSWORD = "oldPassword12@";
    public static final String INVALID_NEW_PASSWORD = "newPassword";
    public static final String CORRECT_NEW_PASSOWRD = "newPassword12&";
    public static final String WRONG_OLD_PASSWORD = "wrongPassword";
    public static final String INVALID_EMAIL = "invalidEmail.com";
    public static final String NON_EXISTING_EMAIL = "nonexistingemail@gmail.com";
    public static final String TEST_EMAIL_ADDRESS = "testEmailAddress3@gmail.com";

}
