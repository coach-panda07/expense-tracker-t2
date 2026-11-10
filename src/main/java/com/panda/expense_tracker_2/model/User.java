package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;

@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String passwordHash;

    @Column(nullable = false)
    LocalDateTime createdAt;


    @PrePersist
    @PreUpdate
    private void normalizeEmail(){
        if(email != null) {
            email = email.trim().toLowerCase(Locale.ROOT);
        }
    }

    public User(String email, LocalDateTime createdAt, String passwordHash) {
        this.email = email;
        this.createdAt = createdAt;
        this.passwordHash = passwordHash;
    }
}
