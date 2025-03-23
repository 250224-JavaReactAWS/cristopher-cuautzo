package com.revature.services;

import com.revature.models.Order;
import com.revature.models.User;
import com.revature.repos.UserDAO;

import java.util.List;
import java.util.regex.Pattern;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) { this.userDAO = userDAO; }

    // REGEX
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{7,}$";
    private static final String PHONE_NUMBER_REGEX = "^\\+?[1-9]?\\d{0,3}[ -]?(\\d{3})[ -]?(\\d{3})[ -.]?(\\d{4})$";

    // Validate data
    public boolean isUsernameAvaiable(String username) {
        return userDAO.getUserByUsername(username) != null;
    }

    public boolean isValidateUsername(String username) {
        return !Pattern.matches(USERNAME_REGEX, username);
    }

    public boolean isValidatePassword(String password) {
        return !Pattern.matches(PASSWORD_REGEX, password);
    }

    public boolean isValidateEmail(String email) {
        return !Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean isValidatePhoneNumber(String phoneNumber) {
        return !Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber);
    }

    // Service Functions
    public List<User> getAllUsers() { return userDAO.getAll(); }

    public User createUser(String firstName, String lastName, String username,
                           String email, String phoneNumber ,String password) {
        User userToSave = new User(firstName, lastName, username, email, phoneNumber, password);

        return userDAO.create(userToSave);
    }

    public User loginUser(String email, String password) {
        User returnedUser = userDAO.getUserByEmail(email);

        if (returnedUser == null) {
            return null;
        }

        if(returnedUser.getPassword().equals(password)) {
            return returnedUser;
        }

        return null;
    }

    public User updateUser(String firstName, String lastName, String username, String email,
                           String phoneNumber, String password, int userId)  {
        User user = userDAO.getById(userId);

        if (user == null) { return null; }

        if (firstName != null) { user.setFirstName(firstName); }
        if (lastName != null) { user.setLastName(lastName); }
        if (username != null) { user.setUsername(username); }
        if (email != null) { user.setEmail(email); }
        if (phoneNumber != null) { user.setPhoneNumber(phoneNumber); }
        if (password != null) { user.setPassword(password); }

        System.out.println(user);

        return userDAO.update(user);
    }

    public List<Order> getAllOrdersById(int id) { return userDAO.orderHistory(id); }
}
