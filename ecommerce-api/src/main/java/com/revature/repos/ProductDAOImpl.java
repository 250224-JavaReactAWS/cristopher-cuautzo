package com.revature.repos;

import com.revature.models.Product;
import com.revature.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO{
    // Get all avaiable products
    @Override
    public List<Product> getAll() {
        List<Product> productsAvailable = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()){

            String sql = "SELECT * FROM products WHERE stock > 0";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                product.setProductId(rs.getInt("product_id"));

                productsAvailable.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We can´t obtain the avaiable products. Try again later");
        }
        return productsAvailable;
    }

    @Override
    public Product getProductByName(String name) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM products WHERE name = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Product product = new Product(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                product.setProductId(rs.getInt("product_id"));
                return product;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("We don´t have a product with that name");
        }
        return null;
    }

    @Override
    public Product create(Product obj) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO products (name, description, price, stock) VALUES " +
                    "(?, ?, ?, ?) RETURNING *;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setDouble(3, obj.getPrice());
            ps.setInt(4, obj.getStock());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Product newProduct = new Product(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                newProduct.setProductId(rs.getInt("product_id"));

                return newProduct;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We can´t add the product");
        }
        return null;
    }

    @Override
    public Product update(Product obj) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ? WHERE product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setDouble(3, obj.getPrice());
            ps.setInt(4, obj.getStock());
            ps.setInt(5,obj.getProductId());

            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated > 0){
                return obj;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We can´t add the product");
        }
        return null;
    }

    @Override
    public Product getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM products WHERE product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Product product = new Product(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                product.setProductId(rs.getInt("product_id"));

                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We can´t add the product");
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM products WHERE product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int deleteRecord = ps.executeUpdate();

            if(deleteRecord > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We can´t add the product");
        }
        return false;
    }
}
