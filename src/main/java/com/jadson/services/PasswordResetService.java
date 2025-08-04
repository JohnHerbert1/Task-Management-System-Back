package com.jadson.services;


import com.jadson.models.entities.PasswordResetToken;
import com.jadson.models.entities.User;
import com.jadson.repositories.PasswordResetTokenRepository;
import com.jadson.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepo;
    private final PasswordResetTokenRepository tokenRepo;
    private final JavaMailSender mailSender;

    @Value("${app.password-reset.expiration-minutes:30}")
    private long expirationMinutes;

    @Value("${app.password-reset.max-attempts:3}")
    private int maxAttempts;

    public void forgotPassword(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado"));
        String token = UUID.randomUUID().toString();

        PasswordResetToken prt = new PasswordResetToken();
        prt.setUser(user);
        prt.setToken(token);
        prt.setExpiryDate(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES));
        tokenRepo.save(prt);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Recuperação de senha");
        msg.setText("Clique para redefinir sua senha:\n" +
                "https://seu-front.com/reset-password?token=" + token);
        mailSender.send(msg);
    }

    /** Checa o token antes de mostrar form de nova senha */
    public void validateToken(String token) {
        PasswordResetToken prt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        // incrementa tentativas e expira se ultrapassar maxAttempts
        prt.incrementAndExpire(maxAttempts);
        tokenRepo.save(prt);

        if (prt.isExpired()) {
            throw new IllegalArgumentException("Token expirado ou inválido");
        }
    }

    /** Aplica nova senha */
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken prt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (prt.isExpired()) {
            throw new IllegalArgumentException("Token expirado");
        }

        User user = prt.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepo.save(user);

        // invalida token
        prt.setExpiryDate(Instant.now());
        tokenRepo.save(prt);
    }
}
