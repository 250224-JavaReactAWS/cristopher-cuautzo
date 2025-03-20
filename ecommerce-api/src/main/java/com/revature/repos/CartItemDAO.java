package com.revature.repos;

import com.revature.models.CartItem;

public interface CartItemDAO extends GeneralDAO<CartItem> {
    /*
     * CRUD Methods
        * CREATE:
            * Add products to the cart => create()
        * UPDATE:
            * The quantity of products => update()
        * DELETE:
            * Remove products from the cart => deleteById()
     */
}
