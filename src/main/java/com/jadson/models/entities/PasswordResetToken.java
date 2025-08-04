package com.jadson.models.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private int attempts = 0;

    public void incrementAndExpire(int maxAttempts) {
        this.attempts++;
        if (this.attempts >= maxAttempts) {
            this.expiryDate = Instant.now(); // expira agora
        }
    }

    public boolean isExpired() {
        return expiryDate.isBefore(Instant.now());
    }

    // getters / setters
}
