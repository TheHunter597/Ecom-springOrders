package com.springOrders.springOrdersMain.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springOrders.entities.Orders;
import com.springOrders.entities.Products;
import com.springOrders.entities.Users;
import com.springOrders.springOrdersMain.controller.ErrorHandlers.BadRequest;
import com.springOrders.springOrdersMain.controller.ErrorHandlers.CheckMissedArgs;
import com.springOrders.springOrdersMain.controller.ErrorHandlers.MissedArgs;
import com.springOrders.springOrdersMain.repos.Orders.OrdersRepo;
import com.springOrders.springOrdersMain.repos.Products.ProductsRepo;
import com.springOrders.springOrdersMain.repos.Users.UsersDAO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
class RequestBodyData extends CheckMissedArgs {
    private String product;
    private Integer quantity;
    private String size = null;
    private String color;

    public String getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "RequestBody [product=" + product + ", quantity=" + quantity + "]";
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}

@RestController
@RequestMapping("/api/v1/orders")
public class MainRestController {
    @Autowired
    UsersDAO usersRepo;

    @Autowired
    OrdersRepo ordersRepo;

    @Autowired
    ProductsRepo productsRepo;

    @Autowired
    com.springOrders.springOrdersMain.repos.Orders.OrdersRepo OrdersRepo;

    @PostMapping("/")
    public ResponseEntity<HashMap<String, Object>> createOrder(@RequestBody RequestBodyData data)
            throws IllegalArgumentException, IllegalAccessException {

        var missedArgs = data.validate(new ArrayList<>(List.of("product", "quantity", "color")));
        if (missedArgs != null) {
            throw new MissedArgs(missedArgs);
        }
        var currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user = usersRepo.loadUser(currentUser.getUsername());

        var product = productsRepo.findById(data.getProduct());
        if (!product.isPresent()) {
            throw new BadRequest("Product not found");
        }

        var order = new Orders(product.get(), user, data.getQuantity(), data.getSize(), data.getColor());
        var newOrder = ordersRepo.save(order);

        var response = new HashMap<String, Object>();
        response.put("message", "Order created successfully");
        response.put("new_order", newOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("multi/")
    public ResponseEntity<HashMap<String, Object>> postMethodName(@RequestBody ArrayList<RequestBodyData> data)
            throws IllegalArgumentException, IllegalAccessException {
        var newOrders = new ArrayList<Orders>();
        for (RequestBodyData element : data) {
            var result = createOrder(element);
            newOrders.add((Orders) result.getBody().get("new_order"));
        }
        var response = new HashMap<String, Object>();
        response.put("message", "Orders created successfully");
        response.put("new_orders", newOrders);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<HashMap<String, Object>> getOrders() {

        var currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = usersRepo.loadUser(currentUser.getUsername());

        var orders = ordersRepo.findOrdersByUser(user);

        var response = new HashMap<String, Object>();
        response.put("message", "Orders fetched successfully");
        response.put("orders", orders);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
