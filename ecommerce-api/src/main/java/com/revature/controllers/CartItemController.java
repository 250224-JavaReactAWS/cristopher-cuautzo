package com.revature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.response.ErrorMessage;
import com.revature.models.CartItem;
import com.revature.services.CartItemService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CartItemController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(CartItemController.class);
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) { this.cartItemService = cartItemService; }

    public void newCartItemHandler(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        Integer sessionUserId = Integer.parseInt(sessionUserIdObj.toString());

        if (ctx.sessionAttribute("userId") == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged to see your cart"));
            return;
        }

        Map<String, String> mapDataToNewCartItem;
        try {
            mapDataToNewCartItem = mapper.readValue(ctx.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        int productId = Integer.parseInt(mapDataToNewCartItem.getOrDefault("productId", "1"));
        int quantity = Integer.parseInt(mapDataToNewCartItem.getOrDefault("quantity", "1"));

        CartItem addItem = cartItemService.newCartItem( sessionUserId, productId, quantity);

        if(addItem == null) {
            ctx.status(500);
            ctx.json(new ErrorMessage("Something went wrong!"));
            return;
        }


        ctx.status(201);
        ctx.json(addItem);
        logger.warn("The product with the id: " + addItem.getProductId()
                + ", was added successfully to the cart of the user with id: " + sessionUserId);
    }

    public void getAllCartItemsHandler(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        Integer sessionUserId = Integer.parseInt(sessionUserIdObj.toString());

        if (ctx.sessionAttribute("userId") == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged into to see your cart"));
            return;
        }

        if (!sessionUserId.equals(sessionUserId)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You are not the user with the id: " + sessionUserId));
            return;
        }

        List<CartItem> cart = cartItemService.getAllCartItems(sessionUserId);

        if(cart.isEmpty()) {
            ctx.status(400);
            ctx.json(new ErrorMessage("Your cart is empty"));
            return;
        }

        ctx.status(201);
        ctx.json(cart);
    }

    public void updateQuantityOfItem(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        String sessionUserIdString = sessionUserIdObj != null ? sessionUserIdObj.toString() : null;
        int sessionUserIdInt = sessionUserIdObj != null ? Integer.parseInt(sessionUserIdObj.toString()) : null;
        int productId = Integer.parseInt(ctx.pathParam("productId"));

        if (sessionUserIdString == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged to see your cart"));
            return;
        }

        JsonNode parent;
        try {
            parent = mapper.readTree(ctx.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        int quantity = (parent.get("quantity").asInt() > 0) ? parent.get("quantity").asInt() : 1;

        CartItem cartItemUpdated = cartItemService.updateQuantityOfItem(quantity, productId, sessionUserIdInt);

        if ( cartItemUpdated == null ) {
            ctx.status(404);
            ctx.json(new ErrorMessage("Cannot update the quantity of the Item"));
            return;
        }

        ctx.status(200);
        ctx.json(cartItemUpdated);
        logger.info("The quantity of the item with the id: " + productId +
                ", on the cart of user with id: " + sessionUserIdInt + " was updated");
    }

    public void removeCartItem(Context ctx) {
        Object sessionUserIdObj = ctx.sessionAttribute("userId");
        int sessionUserIdInt = sessionUserIdObj != null ? Integer.parseInt(sessionUserIdObj.toString()) : null;
        int productId = Integer.parseInt(ctx.pathParam("productId"));

        if (ctx.sessionAttribute("userId") == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to updated your account info"));
            return;
        }

        boolean isDeleted = cartItemService.removeCartItem(productId, sessionUserIdInt);

        if(!isDeleted) {
            ctx.status(404);
            ctx.json(new ErrorMessage("Cannot remove the product from the cart"));
            return;
        }

        ctx.status(204);
        logger.warn("The product with id: " + productId +
                ", was removed successful from the cart of User with Id: " + sessionUserIdInt);
    }
}
