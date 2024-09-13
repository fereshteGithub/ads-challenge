package com.example.config.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.description}")
    private String applicationDescription;

    @Value("${build.version}")
    private String buildVersion;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(applicationName)
                                .description(applicationDescription)
                                .version(buildVersion));
    }

}
