package com.springOrders.springOrdersMain.security.customUserDetails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springOrders.entities.Users;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtUserDetails implements UserDetails {

    Users user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActive();

    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

}
