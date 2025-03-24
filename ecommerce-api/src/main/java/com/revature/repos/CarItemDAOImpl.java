package com.revature.repos;

import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarItemDAOImpl implements CartItemDAO{
    @Override
    public CartItem create(CartItem obj) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO cartitems (user_id, product_id, quantity) VALUES" +
                    "(?, ?, ?) RETURNING *;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getUserId());
            ps.setInt(2, obj.getProductId());
            ps.setInt(3, obj.getQuantity());

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                CartItem newCartItem = new CartItem(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );

                newCartItem.setCartItemId(rs.getInt("cart_item_id"));

                return newCartItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot add the product to the cart");
        }
        return  null;
    }

    @Override
    public CartItem update(CartItem obj) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE cartitems SET quantity = ? WHERE user_id = ? AND product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getQuantity());
            ps.setInt(2, obj.getUserId());
            ps.setInt(3, obj.getProductId());

            int row = ps.executeUpdate();

            if(row > 0) {
                return obj;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot update the quantity of items.");
        }
        return  null;
    }

    @Override
    public boolean deleteCartItemByProductId(int productId, int userId) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM cartitems WHERE user_id = ? AND product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);

            int rows = ps.executeUpdate();

            if(rows > 0) {
                return true;
            } else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot delete the item from the cart.");
        }
        return false;
    }

    @Override
    public CartItem getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM orderitems WHERE order_item_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                CartItem cartItem = new CartItem(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );

                cartItem.setCartItemId(rs.getInt("cart_item_id"));

                return cartItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot get an item with that id");
        }
        return null;
    }

    @Override
    public CartItem getCartItemByProductId(int productId, int userId) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM cartitems WHERE product_id = ? AND user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                CartItem cartItem = new CartItem(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );

                cartItem.setCartItemId(rs.getInt("cart_item_id"));

                return cartItem;
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find the Cart Item");
        }
        return null;
    }

    @Override
    public List<CartItem> getAllCartItemsByUserId(int id) {
        List<CartItem> allCartItems = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM cartitems WHERE user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                CartItem cartItem = new CartItem(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );

                cartItem.setCartItemId(rs.getInt("cart_item_id"));

                allCartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot get the items from your cart.");
        }
        return allCartItems;
    }

    @Override
    public List<CartItem> getAll() { return List.of(); }

    @Override
    public boolean deleteById(int id) {return false;}
}
