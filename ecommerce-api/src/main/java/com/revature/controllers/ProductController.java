package com.revature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.response.ErrorMessage;
import com.revature.models.Product;
import com.revature.models.Role;
import com.revature.services.ProductService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ProductController {
    ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    public void getProductsAvailable(Context ctx) {
        if(productService.getProductsAvailable().isEmpty()) {
            ctx.status(404);
            ctx.json(new ErrorMessage("For the moment we don´t have stock. Try again later"));
        } else {
            ctx.status(200);
            ctx.json(productService.getProductsAvailable());
        }
    }

    public void getProductByName(Context ctx) {
        String name = ctx.queryParam("name");
        if (name == null) {
            ctx.status(400);
            ctx.json(new ErrorMessage("You need to add a query param with name as key"));
            return;
        }

        if (productService.getProductByName(name) == null){
            ctx.status(404);
            ctx.json(new ErrorMessage("We don´t have that product. Maybe later"));
        } else {
            ctx.status(200);
            ctx.json(productService.getProductByName(name));
        }
    }

    public void addProductHandler(Context ctx) {
        Product newProduct = ctx.bodyAsClass(Product.class);
        Object sessionRoleObj = ctx.sessionAttribute("role");
        Role sessionRole = sessionRoleObj != null ? Role.valueOf(sessionRoleObj.toString()) : null;

        if (ctx.sessionAttribute("userId") == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to add a product"));
            return;
        }

        if (!sessionRole.equals(Role.ADMIN)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You don´t have permissions to create a product"));
            return;
        }

        Product addProduct = productService.addProduct(
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getPrice(),
                newProduct.getStock()
        );

        if(addProduct == null) {
            ctx.status(500);
            ctx.json(new ErrorMessage("Something went wrong!"));
            return;
        }

        logger.warn("New product added with the name: " + addProduct.getName());

        ctx.status(201);
        ctx.json(addProduct);
    }

    public void updateProductHandler(Context ctx) {
        Object sessionRoleObj = ctx.sessionAttribute("role");
        Role sessionRole = sessionRoleObj != null ? Role.valueOf(sessionRoleObj.toString()) : null;
        int pathId = Integer.parseInt(ctx.pathParam("productId"));

        if (ctx.sessionAttribute("userId") == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to update a product"));
            return;
        }

        if (!sessionRole.equals(Role.ADMIN)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You don´t have permissions to update a product"));
            return;
        }

        Map<String, String> mapDataToUpdate;
        try {
            mapDataToUpdate = mapper.readValue(ctx.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = mapDataToUpdate.getOrDefault("name", null);
        String description = mapDataToUpdate.getOrDefault("description", null);
        Double price = Double.valueOf(mapDataToUpdate.getOrDefault("price", "0"));
        int stock =  Integer.parseInt(mapDataToUpdate.getOrDefault("stock", "0"));

        Product updatedProduct = productService.updateProductInfo(name, description, price, stock, pathId);

        if ( updatedProduct == null ) {
            ctx.status(404);
            ctx.json(new ErrorMessage("We can´t update the product details"));
            return;
        }

        ctx.status(200);
        ctx.json(updatedProduct);
        logger.info("The product with the id " + pathId + " was updated");
    }

    public void deleteProductHandler(Context ctx){
        int productId = Integer.parseInt(ctx.pathParam("productId"));
        Object sessionRoleObj = ctx.sessionAttribute("role");
        Role sessionRole = sessionRoleObj != null ? Role.valueOf(sessionRoleObj.toString()) : null;

        if (ctx.sessionAttribute("userId") == null) {
            ctx.status(401);
            ctx.json(new ErrorMessage("You must be logged in to delete a product"));
            return;
        }

        if (!sessionRole.equals(Role.ADMIN)) {
            ctx.status(403);
            ctx.json(new ErrorMessage("You don´t have permissions to delete a product"));
            return;
        }

        boolean isDeleted = productService.deleteProductById(productId);

        if(isDeleted == false) {
            ctx.status(404);
            ctx.json(new ErrorMessage("We can´t delete the product"));
            return;
        }

        ctx.status(204);
        logger.warn("The product with id " + productId + " was deleted successful");
    }
}
