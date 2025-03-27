package com.revature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.response.ErrorMessage;
import com.revature.models.Order;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.services.OrderService;
import com.revature.services.ProductService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OrderController {
    ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    public void createAnOrderHandler(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        String sessionUserIdString = sessionUserIdObj != null ? sessionUserIdObj.toString() : null;
        int sessionUserIdInt = sessionUserIdObj != null ? Integer.parseInt(sessionUserIdString) : null;

        if (sessionUserIdString == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to create an order"));
            return;
        }

        Order newOrder = orderService.createAnOrder(sessionUserIdInt);

        if(newOrder == null) {
            ctx.status(500);
            ctx.json(new ErrorMessage("You don´t have items on your cart"));
            return;
        }

        logger.warn("New Order was created by the user with id: " + sessionUserIdString);

        ctx.status(201);
        ctx.json(newOrder);
    }

    public void updateAnOrderHandler(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        String sessionUserIdString = sessionUserIdObj != null ? sessionUserIdObj.toString() : null;

        Object sessionRoleObj = ctx.sessionAttribute("role");
        Role sessionRole = sessionRoleObj != null ? Role.valueOf(sessionRoleObj.toString()) : null;

        int orderId = Integer.parseInt(ctx.pathParam("orderId"));

        if (sessionUserIdString == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to modify orders"));
            return;
        }

        if (!sessionRole.equals(Role.ADMIN)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You don´t have permissions to update an order"));
            return;
        }

        Map<String, String> mapDataToUpdate;
        try {
            mapDataToUpdate = mapper.readValue(ctx.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String status = mapDataToUpdate.getOrDefault("status", null);

        Order updatedOrder = orderService.updateAnOrder(Status.valueOf(status.toUpperCase()), orderId);

        if ( updatedOrder == null ) {
            ctx.status(404);
            ctx.json(new ErrorMessage("We can´t update the status of an order"));
            return;
        }

        ctx.status(200);
        ctx.json(updatedOrder);
        logger.info("The order with the id " +  orderId + " change her status to " + status);
    }

    public void getAllOrdersByStatus(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        String sessionUserIdString = sessionUserIdObj != null ? sessionUserIdObj.toString() : null;

        Object sessionRoleObj = ctx.sessionAttribute("role");
        Role sessionRole = sessionRoleObj != null ? Role.valueOf(sessionRoleObj.toString()) : null;

        String status = ctx.pathParam("status");

        if (sessionUserIdString == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in"));
            return;
        }

        if (!sessionRole.equals(Role.ADMIN)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You must be logged in as an ADMIN to view orders"));
            return;
        }


        ctx.json(200);
        ctx.json(orderService.getAllOrdersByStatus(Status.valueOf(status.toUpperCase())));
    }
    ;
    public void getAllOrders(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        String sessionUserIdString = sessionUserIdObj != null ? sessionUserIdObj.toString() : null;

        Object sessionRoleObj = ctx.sessionAttribute("role");
        Role sessionRole = sessionRoleObj != null ? Role.valueOf(sessionRoleObj.toString()) : null;

        if (sessionUserIdString == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in"));
            return;
        }

        if (!sessionRole.equals(Role.ADMIN)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You must be logged in as an ADMIN to view orders"));
            return;
        }

        ctx.json(200);
        ctx.json(orderService.getAllOrders());
    };
}
