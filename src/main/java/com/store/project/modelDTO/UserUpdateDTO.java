package com.store.project.modelDTO;

public class UserUpdateDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    private UserUpdateDTO() {}

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
