package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.User;
import com.store.project.repository.UserRepository;
import com.store.project.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        try {
            boolean passwordIsValidated = Util.validatePassword(user.getPassword());
            boolean emailIsValidated = Util.validateEmail(user.getEmail());

            if (!passwordIsValidated) {
                throw new CustomExceptions.InvalidPasswordException("Password does not meet the criteria.");
            }
            if (!emailIsValidated) {
                throw new CustomExceptions.InvalidEmailException("Email is not valid.");
            }
            if (getUserByEmail(user.getEmail()) != null) {
                throw new CustomExceptions.UserAlreadyExistsException("User with this email already exists.");
            }

            return userRepository.save(user);
        } catch (CustomExceptions.InvalidPasswordException | CustomExceptions.InvalidEmailException |
                 CustomExceptions.InvalidPhoneNumberException | CustomExceptions.UserAlreadyExistsException e) {
            throw new RuntimeException("User can not be registered: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during user registration", e);
        }
    }

    public User loginUser(User user) {
        try {
            User existingUser = getUserByEmail(user.getEmail());
            if (existingUser == null) {
                throw new CustomExceptions.UserNotFoundException("User with this email does not exist.");
            }
            if (user.getPassword().equals(existingUser.getPassword())) {
                throw new CustomExceptions.InvalidPasswordException("Invalid password.");
            }
            return existingUser;
        } catch (CustomExceptions.UserNotFoundException | CustomExceptions.InvalidPasswordException e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during login", e);
        }
    }

    public User getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email).get();
        } catch(Exception e) {
            throw new RuntimeException("User can not be found ", e);
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

}
