package com.ecomptaia.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private String firstName;
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    
    private Boolean active = true;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastLogin;
    
    public enum Role {
        ADMIN, ACCOUNTANT, USER, VIEWER
    }
}
