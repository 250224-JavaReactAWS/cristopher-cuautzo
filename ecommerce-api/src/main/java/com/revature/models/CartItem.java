package com.revature.models;

public class CartItem {
    private int cartItemId;
    private int userId;
    private int productId;
    private int quantity;

    private static int cartItemIdCounter = 1;

    public CartItem(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;

        this.cartItemId = cartItemIdCounter;
        cartItemIdCounter++;
    }

    public int getCartItemId() { return cartItemId; }

    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }

    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "CartItem {" +
                "cartItemId = " + cartItemId +
                ", userId = " + userId +
                ", productId = " + productId +
                ", quantity = " + quantity +
                "}";
    }
}