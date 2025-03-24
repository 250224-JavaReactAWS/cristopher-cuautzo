package com.revature.repos;

import com.revature.models.Order;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    @Override
    public List<Order> orderHistory(int userId) {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()){

        String sql = "SELECT * FROM orders WHERE user_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order returnedOrder = new Order(rs.getInt("user_id"), rs.getDouble("total_price"),
                    Status.valueOf(rs.getString("status")), rs.getDate("created_at"));

            returnedOrder.setOrderId(rs.getInt("order_id"));

            orders.add(returnedOrder);
        }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We don´t find Orders with that Id");
        }
        return orders;
    }

    @Override
    public User getUserByEmail(String email) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM users WHERE email = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("email"),
                        rs.getString("phone_number"), rs.getString("password"));

                u.setUserId(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can´t get an account with that email");
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM users WHERE username = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("email"),
                        rs.getString("phone_number"), rs.getString("password"));

                u.setUserId(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can´t get an account with that username");
        }
        return null;
    }

    @Override
    public User getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id); ;

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User returnedUser = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("email"),
                        rs.getString("phone_number"), rs.getString("password"));

                returnedUser.setUserId(rs.getInt("user_id"));
                returnedUser.setRole(Role.valueOf(rs.getString("role")));

                return returnedUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can´t get an account with that id");
        }
        return null;
    }

    @Override
    public User create(User obj) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO Users (first_name, last_name, username, email, phone_number, password) VALUES " +
                    "(?, ?, ?, ?, ?, ?) RETURNING *;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, obj.getFirstName());
            ps.setString(2, obj.getLastName());
            ps.setString(3, obj.getUsername());
            ps.setString(4, obj.getEmail());
            ps.setString(5, obj.getPhoneNumber());
            ps.setString(6, obj.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();

                u.setUserId(rs.getInt("user_id"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPhoneNumber(rs.getString("phone_number"));
                u.setPassword(rs.getString("password"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Could not save the user :(");
        }
        return null;
    }

    @Override
    public User update(User obj) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, email = ?, phone_number = ?, password = ? WHERE user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getFirstName());
            ps.setString(2, obj.getLastName());
            ps.setString(3, obj.getUsername());
            ps.setString(4, obj.getEmail());
            ps.setString(5, obj.getPhoneNumber());
            ps.setString(6, obj.getPassword());
            ps.setInt(7, obj.getUserId());

            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated > 0) {
                return obj;
            } else {
                return null;
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to update the account");
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Users";
            Statement stmt =conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPhoneNumber(rs.getString("phone_number"));
                u.setPassword(rs.getString("password"));
                u.setRole(Role.valueOf(rs.getString("role")));

                allUsers.add(u);
            }

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We cant get all the users!");
        }
        return allUsers;
    }

    @Override
    public boolean deleteById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM users WHERE user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Dons´t exists a user with that id");
        }
        return false;
    }
}
