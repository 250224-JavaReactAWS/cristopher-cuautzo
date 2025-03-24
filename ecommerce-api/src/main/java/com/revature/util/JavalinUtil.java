package com.revature.util;

import com.revature.controllers.ProductController;
import com.revature.controllers.UserController;
import com.revature.repos.ProductDAO;
import com.revature.repos.ProductDAOImpl;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import com.revature.services.ProductService;
import com.revature.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinUtil {
    public static Javalin create(int port){
        UserDAO userDAO = new UserDAOImpl();
        UserService userService = new UserService(userDAO);
        UserController userController = new UserController(userService);

        ProductDAO productDAO = new ProductDAOImpl();
        ProductService productService = new ProductService(productDAO);
        ProductController productController = new ProductController(productService);

        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/users", () -> {
                    post("/register", userController:: registerUserHandler);
                    post("/login", userController:: loginHandler);
                    put("/{userId}", userController:: updateUserHandler);
                    get("/orders/{userId}", userController:: getAllOrdersById);
                });
                path("/products", () -> {
                    get("/available", productController:: getProductsAvailable);
                    get("/{name}", productController:: getProductByName);
                    post("/new", productController:: addProductHandler);
                    post("/{productId}", productController:: updateProductHandler);
                    delete("/{productId}", productController:: deleteProductHandler);
                });
            });
        }).start(port);
    }
}
