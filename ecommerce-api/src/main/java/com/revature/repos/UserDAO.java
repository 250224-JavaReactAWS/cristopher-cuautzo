package com.revature.repos;

import com.revature.models.Order;
import com.revature.models.User;

import java.util.List;

/*
    * CRUD Methods
        * CREATE:
            * An account => create();
        * READ:
            * Get User => getById(), getUUserByUsername()
            * My order history => viewMyOrderHistory()
        * UPDATE:
            *  My accounts details (name, email, password) => update()
*/

public interface UserDAO extends GeneralDAO<User> {
    List<Order> viewMyOrderHistory(String username);
    User getUserByUsername(String Username);
}
