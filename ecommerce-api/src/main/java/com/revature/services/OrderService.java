package com.revature.services;

import com.revature.models.*;
import com.revature.repos.*;

import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
    private final CartItemDAO cartItemDAO = new CarItemDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    public OrderService (OrderDAO orderDAO) { this.orderDAO = orderDAO; }

    // TODO: Use TCL for this
    public Order createAnOrder(int userId) {
        if(cartItemDAO.getAllCartItemsByUserId(userId).isEmpty()) {
            return null;
        }

        double price = 0;

        for(CartItem item : cartItemDAO.getAllCartItemsByUserId(userId)) {
            Product product = productDAO.getById(item.getProductId());
            price += product.getPrice() * item.getQuantity();
            product.setStock(product.getStock() - item.getQuantity());
        }

        Order newOrder = new Order(userId, price);

        for(CartItem item : cartItemDAO.getAllCartItemsByUserId(userId)) {
            CartItem cartItem = new CartItem(userId, item.getProductId(), item.getQuantity());
            cartItemDAO.create(cartItem);
        }

        cartItemDAO.deleteById(userId);
        return orderDAO.create(newOrder);
    }

    public Order updateAnOrder(Status status, int orderId) {
        Order orderToUpdate = orderDAO.getById(orderId);

        if(orderToUpdate == null) { return null; }

        orderToUpdate.setStatus(status);

        return orderDAO.update(orderToUpdate);
    }

    public List<Order> getAllOrders() { return orderDAO.getAll(); }

    public List<Order> getAllOrdersByStatus(Status status) { return orderDAO.getAllByStatus(status); }
}
