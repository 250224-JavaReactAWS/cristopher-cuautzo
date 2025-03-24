package com.revature.services;

import com.revature.models.Product;
import com.revature.repos.ProductDAO;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) { this.productDAO = productDAO; }

    public List<Product> getProductsAvailable() { return  productDAO.getAll(); }

    public Product getProductByName(String name) { return productDAO.getProductByName(name); }

    public Product addProduct(String name, String description, Double price, int stock) {
        Product newProduct = new Product(name, description, price, stock);

        return productDAO.create(newProduct);
    }

    public Product updateProductInfo(String name, String description, Double price, int stock, int productId) {
        Product productToUpdate =  productDAO.getById(productId);

        if (productToUpdate == null) { return null; }

        if (name != null) { productToUpdate.setName(name); }
        if (description != null) { productToUpdate.setDescription(description); }
        if (price != 0) { productToUpdate.setPrice(price); }
        if (stock != 0) { productToUpdate.setStock(stock); }

        return productDAO.update(productToUpdate);
    }

    public boolean deleteProductById(int productId) {  return productDAO.deleteById(productId); }
}
