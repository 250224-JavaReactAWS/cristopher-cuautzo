package com.revature.models;

import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private double totalPrice;
    private Status status;
    private Date createdAt;

    private static int orderIdCounter = 1;

    public Order(int userId, double totalPrice) {
        this.userId = userId;
        this.totalPrice = totalPrice;

        this.createdAt = new Date();
        this.status = Status.PENDING;

        this.orderId = orderIdCounter;
        orderIdCounter++;
    }

    public Order(){}

    public int getOrderId() { return orderId; }

    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public double getTotal_price() { return totalPrice; }

    public void setTotal_price(double totalPrice) { this.totalPrice = totalPrice; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Order {" +
                "orderId = " + orderId +
                ", userId = " + userId +
                ", totalPrice = " + totalPrice +
                ", status = " + status +
                ", createdAt = " + createdAt +
                "}";
    }
}
