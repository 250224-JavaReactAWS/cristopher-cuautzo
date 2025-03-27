package com.revature.repos;

import com.revature.models.Order;
import com.revature.models.Status;
import com.revature.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO{
    @Override
    public List<Order> getAllByStatus(Status status) {
        List<Order> ordersByStatus = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM orders WHERE status = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status.toString());

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Order order = new Order(
                        rs.getInt("user_id"),
                        rs.getDouble("total_price")
                );
                order.setStatus(Status.valueOf(rs.getString("status")));
                order.setOrderId(rs.getInt("order_id"));
                order.setCreatedAt(rs.getDate("created_at"));

                ordersByStatus.add(order);
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("We cannot get the orders");
        }
        return ordersByStatus;
    }

    @Override
    public Order create(Order obj) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO orders (user_id, total_price) VALUES " +
                    "(?, ?) RETURNING *;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getUserId());
            ps.setDouble(2, obj.getTotalPrice());

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Order order = new Order(
                        rs.getInt("user_id"),
                        rs.getDouble("total_price")
                );
                order.setStatus(Status.valueOf(rs.getString("status")));
                order.setOrderId(rs.getInt("order_id"));
                order.setCreatedAt(rs.getDate("created_at"));

                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot create an order");
        }
        return null;
    }

    @Override
    public Order update(Order obj) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getStatus().toString());
            ps.setInt(2, obj.getOrderId());

            int rows = ps.executeUpdate();

            if(rows > 0) {
                return obj;
            } else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot update the order status");
        }
        return null;
    }

    @Override
    public Order getById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM orders WHERE order_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Order order = new Order(
                        rs.getInt("user_id"),
                        rs.getDouble("total_price")
                );
                order.setStatus(Status.valueOf(rs.getString("status")));
                order.setOrderId(rs.getInt("order_id"));
                order.setCreatedAt(rs.getDate("created_at"));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot get the order by id");
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        List<Order> allOrders = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM orders";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("user_id"),
                        rs.getDouble("total_price")
                );
                order.setStatus(Status.valueOf(rs.getString("status")));
                order.setOrderId(rs.getInt("order_id"));
                order.setCreatedAt(rs.getDate("created_at"));

                allOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot get all orders");
        }
        return allOrders;
    }

    @Override
    public boolean deleteById(int id) { return false; }
}
