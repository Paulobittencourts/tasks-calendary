package com.br.hbs.task.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class Configs {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("REST API - To-do List").version("1.0.0").description("Doc for Tasks"));
    }
}