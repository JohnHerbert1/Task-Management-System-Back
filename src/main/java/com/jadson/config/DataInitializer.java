package com.jadson.config;

import com.jadson.models.entities.User;
import com.jadson.models.enumerations.ActiveType;
import com.jadson.models.enumerations.UserType;
import com.jadson.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private final PasswordEncoder encoder;

    public DataInitializer(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            String adminEmail = "admin@admin.com";
            String defaultPassword = "admin123";

            userRepository.findByEmail(adminEmail).ifPresentOrElse(admin -> {
                // Se a senha no banco não for igual à senha padrão codificada, atualiza
                if (!encoder.matches(defaultPassword, admin.getPassword())) {
                    admin.setPassword(encoder.encode(defaultPassword));
                    System.out.println("🔄 Senha do admin atualizada para a padrão.");
                }
                // Se o tipo ou status estiverem incorretos, corrige
                if (admin.getType() != UserType.ADMINISTRADOR) {
                    admin.setType(UserType.ADMINISTRADOR);
                }
                if (admin.getActiveType() != ActiveType.ATIVO) {
                    admin.setActiveType(ActiveType.ATIVO);
                }
                userRepository.save(admin);
                System.out.println("ℹ️ Usuário administrador já existia, dados verificados.");
            }, () -> {
                // Criar novo admin
                User admin = new User();
                admin.setName("Administrador Master");
                admin.setEmail(adminEmail);
                admin.setPassword(encoder.encode(defaultPassword));
                admin.setType(UserType.ADMINISTRADOR);
                admin.setActiveType(ActiveType.ATIVO);
                userRepository.save(admin);
                System.out.println("✅ Usuário administrador criado com sucesso!");
            });
        };
    }
}
