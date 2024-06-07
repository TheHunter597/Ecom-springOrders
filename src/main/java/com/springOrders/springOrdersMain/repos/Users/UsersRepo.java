package com.springOrders.springOrdersMain.repos.Users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springOrders.entities.Users;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class UsersRepo implements UsersDAO {
    @Autowired
    EntityManager entityManager;

    public Users loadUser(String email) {
        return entityManager.createQuery("""
                    select u from Users u
                    where u.email = :email
                """, Users.class).setParameter("email", email).getSingleResult();
    }

    public Users loadUserById(String id) {
        UUID modifiedId = UUID.fromString(id);
        return entityManager.createQuery("""
                    select u from Users u
                    where u.id = :id
                """, Users.class).setParameter("id", modifiedId).getSingleResult();
    }

    @Transactional
    public void updateUser(Users user) {
        entityManager.merge(user);
    }

    @Transactional
    public void saveUser(Users user) {
        entityManager.persist(user);
    }
}
