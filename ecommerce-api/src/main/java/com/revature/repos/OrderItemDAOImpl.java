package com.revature.repos;

import com.revature.models.OrderItem;
import com.revature.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDAOImpl implements GeneralDAO<OrderItem>{

    @Override
    public OrderItem create(OrderItem obj) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO orderitems (order_id, product_id, quantity) VALUES" +
                    "(?, ?, ?) RETURNING *;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getOrderId());
            ps.setInt(2, obj.getProductId());
            ps.setInt(3, obj.getQuantity());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                OrderItem orderItem = new OrderItem(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price")
                );

                orderItem.setOrderItemId(rs.getInt("order_item_id"));
                return orderItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderItem update(OrderItem obj) {
        return null;
    }

    @Override
    public OrderItem getById(int id) {
        return null;
    }

    @Override
    public List<OrderItem> getAll() {
        return List.of();
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
