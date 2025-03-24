package com.revature.services;

import com.revature.models.CartItem;
import com.revature.repos.CartItemDAO;

public class CartItemService {
    private final CartItemDAO cartItemDAO;

    public CartItemService(CartItemDAO cartItemDAO) { this.cartItemDAO = cartItemDAO; }

    public CartItem newCartItem(int userId, int cartItemId, int quantity) {
        CartItem newCartItem = new CartItem(userId, cartItemId, quantity);

        return cartItemDAO.create(newCartItem);
    }

    public CartItem updateQuantityOfItem(int quantity, int productId, int userId) {
        CartItem cartItem = cartItemDAO.getCartItemByProductId(productId, userId);

        if(cartItem == null) { return null; }

        cartItem.setQuantity(quantity);

        return cartItemDAO.update(cartItem);
        }

    public boolean removeCartItem(int productId, int userId) {
        return cartItemDAO.deleteCartItemByProductId(productId, userId);
    }
}
