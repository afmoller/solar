package moller.solar.solarpersistence.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import moller.solarpersistence.openapi.api.EnergyCostControllerApiController;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .name("Bearer-Authentication");
    }

    @Bean
    public OpenAPI openAPI() {

        SpringDocUtils.getConfig().addRestControllers(EnergyCostControllerApiController.class);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer-Authentication"))
                .components(new Components().addSecuritySchemes("Bearer-Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("Solar persistence REST API")
                        .description("This API exposes APIs used by the Solar backend application and other Solar related applications.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Fredrik Möller")
                                .url("https://github.com/afmoller/solar")));
    }
}
