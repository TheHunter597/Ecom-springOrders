package com.springOrders.springOrdersMain.repos.Users;

import com.springOrders.entities.Users;

public interface UsersDAO {
    Users loadUser(String email);

    void saveUser(Users user);

    Users loadUserById(String id);

    void updateUser(Users user);
}
