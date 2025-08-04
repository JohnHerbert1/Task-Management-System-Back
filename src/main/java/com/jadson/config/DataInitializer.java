package com.jadson.config;   // <-- corrija aqui, remova o 'l'

import com.jadson.dto.requests.UserDTO;
import com.jadson.models.entities.User;
import com.jadson.models.enumerations.ActiveType;
import com.jadson.models.enumerations.UserType;
import com.jadson.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                // Preenche via DTO
                UserDTO dto = new UserDTO("Administrador Master", adminEmail, "admin123");

                // Converte para entidade
                User admin = toEntity(dto);

                // Define tipo e status
                admin.setType(UserType.ADMINISTRADOR);
                admin.setActiveType(ActiveType.ATIVO);

                // Criptografa a senha **antes** de salvar
                admin.setPassword(encoder.encode(dto.password()));

                userRepository.save(admin);
                System.out.println("✅ Usuário administrador criado com sucesso!");
            } else {
                System.out.println("ℹ️ Usuário administrador já existe.");
            }
        };
    }

    private User toEntity(UserDTO dto){
        User u = new User();
        // copia name, email e password (texto puro)
        BeanUtils.copyProperties(dto, u, "id");
        return u;
    }
}
