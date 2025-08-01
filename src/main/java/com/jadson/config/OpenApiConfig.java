package com.jadson.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management System API") // Título
                        .description("Documentação da API do Sistema de Gerenciamento de Tarefas") // Descrição
                        .version("1.0.0") // Versão
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
        // **NÃO ADICIONE MAIS NADA AQUI POR ENQUANTO** (como securitySchemes, securityItems, etc.)
    }
}