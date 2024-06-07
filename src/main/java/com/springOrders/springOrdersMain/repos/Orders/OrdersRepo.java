package com.springOrders.springOrdersMain.repos.Orders;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springOrders.entities.Orders;
import com.springOrders.entities.Users;

@Component
public interface OrdersRepo extends JpaRepository<Orders, UUID> {
    ArrayList<Orders> findOrdersByUser(Users user);
}
