package com.revature.repos;

import com.revature.models.Order;
import com.revature.models.Status;

import java.util.List;

public interface OrderDAO extends GeneralDAO<Order>{
    /*
     * CRUD Methods
        * CREATE:
            * Make an order => create()
        * READ:
            * View a list of orders => getAll() (ONLY ADMIN)
            * View a list of orders filter by status => getAllByStatus() (ONLY ADMIN)
        * UPDATE:
            * Status of the order => update() (ONLY ADMIN)
    */
    List<Order> getAllByStatus(Status status);
}
