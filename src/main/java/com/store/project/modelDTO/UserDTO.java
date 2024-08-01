package com.store.project.modelDTO;

public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private UserDTO(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
