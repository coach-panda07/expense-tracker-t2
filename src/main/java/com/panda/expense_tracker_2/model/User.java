package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private LocalDateTime createdAt;


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
