package com.jadson.config;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desativa proteção CSRF (apenas se não for usar sessão)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Libera todas as rotas sem autenticação
                )
                .formLogin().disable() // Desativa a tela de login padrão
                .httpBasic().disable(); // Desativa o login por prompt do navegador (opcional)

        return http.build();
    }

    @PostConstruct
    public void init() {
        log.info("🔒 SecurityConfig customizada ativa!");
    }
}