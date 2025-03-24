package com.revature.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.response.ErrorMessage;
import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class UserController {
    ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    public void registerUserHandler(Context ctx){
        User user = ctx.bodyAsClass(User.class);

        if(userService.isValidateUsername(user.getUsername())) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid Username. Needs to start with a letter (Uppercase or lowercase) " +
                    "and only can contain uppercase or lowercase letters, numbers and underscore"));
            return;
        }

        if (userService.isValidatePassword(user.getPassword())) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid Password. Must be at least 8 characters long " +
                    "and need contain one uppercase letter, one lower case letter " +
                    "and one special character (@$!%*?&)"));
            return;
        }

        if (userService.isValidateEmail(user.getEmail())) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid email. Valid format is example@domain.com"));
            return;
        }

        if (userService.isValidatePhoneNumber(user.getPhoneNumber())) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid Phone Number. Valid format is (+##)-###-###-#### " +
                    "(The country code is optional)"));
            return;
        }

        if (userService.isUsernameAvaiable(user.getUsername())){
            ctx.status(400);
            ctx.json(new ErrorMessage("Username is not available, please select a new one"));

            logger.warn("Register attempt made for taken username: " + user.getUsername());
            return;
        }

        User registeredUser = userService.createUser(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword()
        );

        if (registeredUser == null){
            ctx.status(500);
            ctx.json(new ErrorMessage("Something went wrong!"));
            return;
        }

        logger.warn("New user registered with username: " + registeredUser.getUsername());

        ctx.status(201);
        ctx.json(registeredUser);
    }

    public void loginHandler(Context ctx) {
        User requestedUser = ctx.bodyAsClass(User.class);

        User returnedUser = userService.loginUser(requestedUser.getEmail(),
                requestedUser.getPassword());

        if(returnedUser == null){
            ctx.json(new ErrorMessage("Username or Password Incorrect"));
            ctx.status(400);
            return;
        }

        ctx.status(200);
        ctx.json(returnedUser);

        ctx.sessionAttribute("userId", returnedUser.getUserId());
        ctx.sessionAttribute("role", returnedUser.getRole());
    }

    public void updateUserHandler(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        String sessionUserId = sessionUserIdObj != null ? sessionUserIdObj.toString() : null;
        String userId = ctx.pathParam("userId");

        if (sessionUserId == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to updated your account info"));
            return;
        }

        if (!sessionUserId.equals(userId)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You are not the user " + userId));
            return;
        }

        Map<String, String> mapDataToUpdate;
        try {
            mapDataToUpdate = mapper.readValue(ctx.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String firstName = mapDataToUpdate.getOrDefault("firstName", null);
        String lastName = mapDataToUpdate.getOrDefault("lastName", null);
        String username = mapDataToUpdate.getOrDefault("username", null);
        String email = mapDataToUpdate.getOrDefault("email", null);
        String phoneNumber = mapDataToUpdate.getOrDefault("phoneNumber", null);
        String password = mapDataToUpdate.getOrDefault("password", null);

        if(username != null && userService.isValidateUsername(username)) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid Username. Needs to start with a letter (Uppercase or lowercase) " +
                    "and only can contain uppercase or lowercase letters, numbers and underscore"));
            return;
        }

        if (password != null && userService.isValidatePassword(password)) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid Password. Must be at least 8 characters long " +
                    "and need contain one uppercase letter, one lower case letter " +
                    "and one special character (@$!%*?&)"));
            return;
        }

        if (email != null && userService.isValidateEmail(email)) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid email. Valid format is example@domain.com"));
            return;
        }

        if (phoneNumber != null && userService.isValidatePhoneNumber(phoneNumber)) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Invalid Phone Number. Valid format is (+##)-###-###-#### " +
                    "(The country code is optional)"));
            return;
        }

        if ((username != null && userService.isUsernameAvaiable(username))){
            ctx.status(400);
            ctx.json(new ErrorMessage("Username is not available, please select a new one"));

            logger.warn("Register attempt made for taken username: " + username);
            return;
        }

        User updatedUser = userService.updateUser(
                firstName,
                lastName,
                username,
                email,
                phoneNumber,
                password,
                Integer.parseInt(userId)
        );

        if(updatedUser == null) {
            ctx.status(404);
            ctx.json(new ErrorMessage("We can´t update account details"));
            return;
        }

        ctx.status(200);
        ctx.json(updatedUser);
        logger.warn("The user with the id " + sessionUserId + " was updated");
    }

    public void getAllOrdersById(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        String sessionUserId = sessionUserIdObj != null ? sessionUserIdObj.toString() : null;
        String pathUserId = ctx.pathParam("userId");

        if (sessionUserId == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to view your orders!"));
            return;
        }

        if (!sessionUserId.equals(pathUserId)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You are not the user " + pathUserId));
            return;
        }

        ctx.json(userService.getAllOrdersById(Integer.parseInt(pathUserId)));
    }

}
