package com.springOrders.springOrdersMain.repos.Products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.springOrders.entities.Products;

@Component
public interface ProductsRepo extends JpaRepository<Products, String> {

}