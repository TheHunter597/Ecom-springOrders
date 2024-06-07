package com.springOrders.kafka.Consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.springOrders.springOrdersMain.repos.Products.ProductsRepo;

class ProductDeletedData {
    public String id;
}

@Service
public class ProductDeletedConsumer {

    @Autowired
    ProductsRepo productsRepo;

    @Autowired
    ObjectMapper mapper;

    @KafkaListener(topics = "product-deleted", groupId = "SpringOrdersProductDeleted")
    public void listen(ConsumerRecord<String, String> data) {
        try {
            var serializedData = mapper.readValue(data.value(), ProductDeletedData.class);

            productsRepo.deleteById(serializedData.id);
            System.out.println("Product Deleted" + serializedData.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
