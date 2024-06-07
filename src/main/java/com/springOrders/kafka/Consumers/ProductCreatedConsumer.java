package com.springOrders.kafka.Consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springOrders.entities.Products;
import com.springOrders.springOrdersMain.repos.Products.ProductsRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class KafkaProductCreated {
    private String title;
    private double price;
    private String image;
    private int countInStock;
    private String id;

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getCountInStock() {
        return countInStock;
    }

    public String getId() {
        return id;
    }
}

@Service
public class ProductCreatedConsumer {

    @Autowired
    ProductsRepo productsRepo;

    @Autowired
    ObjectMapper mapper;

    @KafkaListener(topics = "product-created", groupId = "SpringOrdersProductCreated")
    public void listen(ConsumerRecord<String, String> data) {
        System.out.println("User created: " + data);
        System.out.println(data.value().getClass());
        try {
            var serializedData = mapper.readValue(data.value(), Products.class);
            System.out.println(serializedData);
            productsRepo.save(serializedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
