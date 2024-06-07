package com.springOrders.springOrdersMain.security.customUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springOrders.springOrdersMain.repos.Users.UsersDAO;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UsersDAO usersDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = usersDAO.loadUser(email);
        if (user.isActive() == false) {
            return null;
        }
        return new JwtUserDetails(user);
    }

}
