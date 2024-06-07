package com.springOrders.kafka.Consumers;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springOrders.entities.Products;
import com.springOrders.springOrdersMain.repos.Products.ProductsRepo;

class ProductUpdatedData {
    public String id;
    public Products data;
}

@Service
public class ProductUpdatedConsumer {

    @Autowired
    ProductsRepo productsRepo;

    @Autowired
    ObjectMapper mapper;

    @KafkaListener(topics = "product-updated", groupId = "SpringOrdersProductUpdated")
    public void listen(ConsumerRecord<String, String> data) {
        System.out.println("User Updated: " + data);
        System.out.println(data.value());
        try {
            var serializedData = mapper.readValue(data.value(), ProductUpdatedData.class);

            Optional<Products> currentProduct = productsRepo.findById(serializedData.id);
            if (currentProduct.isPresent()) {
                System.out.println("Product found");
                Products product = currentProduct.get();
                product.updateProduct(serializedData.data);
                productsRepo.save(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}