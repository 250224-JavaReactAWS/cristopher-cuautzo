package com.revature.repos;

import com.revature.models.Product;

import java.util.List;

public interface ProductDAO extends GeneralDAO<Product>{
    /*
     * CRUD Methods
        * CREATE:
            * New products => create() (ONLY ADMIN)
        * READ:
            * Avaiable products => getAll(), getAvaiableProducts();
            * Details of a specific products => getProductByName(), getById()
        * UPDATE:)
            * Products details (name, price, stock) => update() (ONLY ADMIN)
        * DELETE:
            * Delete products => deleteById() (ONLY ADMIN)
     */
    List<Product> getAvaiableProducts();
    Product getProductByName(String name);
}
