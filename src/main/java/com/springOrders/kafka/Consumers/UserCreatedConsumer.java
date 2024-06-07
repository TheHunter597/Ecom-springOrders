package com.springOrders.kafka.Consumers;

import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springOrders.entities.Users;
import com.springOrders.springOrdersMain.repos.Users.UsersDAO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
class ConsumerRecordData {
    private String email;
    private String id;

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return true;
    }

    public String getId() {
        return id;
    }

}

@Service
public class UserCreatedConsumer {

    @Autowired
    UsersDAO usersDAO;

    @Autowired
    ObjectMapper mapper;

    @KafkaListener(topics = "user-created", groupId = "SpringOrdersUserCreated")
    public void listen(ConsumerRecord<String, String> data) {
        System.out.println("User created: " + data);
        System.out.println(data.value().getClass());
        try {
            var serializedData = mapper.readValue(data.value(), ConsumerRecordData.class);
            Users user = new Users(UUID.fromString(serializedData.getId()), serializedData.getEmail(),
                    serializedData.isActive());
            usersDAO.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
