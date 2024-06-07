package com.springOrders.entities;

import java.util.UUID;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users implements Persistable<UUID> {
    @Id
    private UUID id;

    @Column(name = "email")
    private String email;
    @Column(name = "is_active")
    private boolean isActive;

    public Users(UUID id, String email, boolean isActive) {
        this.id = id;
        this.email = email;
        this.isActive = isActive;
    }

    public boolean isNew() {
        return id == null;
    }
}
