    package com.jadson.services;


    import com.jadson.models.entities.PasswordResetToken;
    import com.jadson.models.entities.User;
    import com.jadson.repositories.PasswordResetTokenRepository;
    import com.jadson.repositories.UserRepository;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.time.Instant;
    import java.time.temporal.ChronoUnit;
    import java.util.UUID;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PasswordResetService {

        private final UserRepository userRepo;
        private final PasswordResetTokenRepository tokenRepo;
        private final JavaMailSender mailSender;
        private final PasswordEncoder passwordEncoder;

        @Value("${app.password-reset.expiration-minutes:30}")
        private long expirationMinutes;

        @Value("${app.password-reset.max-attempts:3}")
        private int maxAttempts;

        @Value("${app.frontend.reset-url}")
        private String frontendResetUrl;

        public void forgotPassword(String email) {
            User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado"));
            String token = UUID.randomUUID().toString();

            PasswordResetToken prt = new PasswordResetToken();
            prt.setUser(user);
            prt.setToken(token);
            prt.setExpiryDate(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES));
            tokenRepo.save(prt);

            // Usa a URL do frontend configurada nas properties
            String frontendUrl = frontendResetUrl; // inject via @Value abaixo
            String resetLink = frontendUrl + "?token=" + java.net.URLEncoder.encode(token, java.nio.charset.StandardCharsets.UTF_8);

            log.info("Password reset token gerado para {} : {}", email, token);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("taskmensg@gmail.com");
            msg.setTo(email);
            msg.setSubject("Recuperação de senha");
            msg.setText("Clique para redefinir sua senha:\n" + resetLink
                    + "\n\nSe não conseguir abrir o link, copie este token:\n" + token);
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
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);

            // invalida token
            prt.setExpiryDate(Instant.now());
            tokenRepo.delete(prt);
        }
    }
