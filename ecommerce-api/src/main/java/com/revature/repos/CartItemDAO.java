package com.revature.repos;

import com.revature.models.CartItem;

import java.util.List;

public interface CartItemDAO extends GeneralDAO<CartItem> {
    /*
     * CRUD Methods
        * CREATE:
            * Add products to the cart => create()
        * UPDATE:
            * The quantity of products => update()
        * DELETE:
            * Remove products from the cart => deleteById()
         * GET:
            * Get all the items from the cart => getAllCartItemsByUserId(), getCartItemByProductId()
     */
    List<CartItem> getAllCartItemsByUserId(int id);
    CartItem getCartItemByProductId(int productId, int userId);
    boolean deleteCartItemByProductId(int productId, int userId);
}