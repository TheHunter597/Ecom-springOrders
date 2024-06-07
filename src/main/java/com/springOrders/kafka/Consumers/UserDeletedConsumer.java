package com.springOrders.kafka.Consumers;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springOrders.entities.Users;
import com.springOrders.springOrdersMain.repos.Users.UsersDAO;

@Service
public class UserDeletedConsumer {

    @Autowired
    UsersDAO usersDAO;

    @Autowired
    ObjectMapper mapper;

    @KafkaListener(topics = "user-deleted", groupId = "SpringOrdersUserDeleted")
    public void listen(ConsumerRecord<String, String> data) {
        System.out.println("User Deleted: " + data);
        System.out.println(data.value().getClass());
        try {
            var serializedData = mapper.readValue(data.value(), HashMap.class);

            System.out.println(serializedData); // ConsumerRecordData instance with correct values

            Users user = usersDAO.loadUserById((String) serializedData.get("user_id"));
            if (user == null) {
                return;
            }
            user.setActive(false);
            usersDAO.updateUser(user);
            System.out.println("User Deleted: " + user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
