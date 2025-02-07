package moller.solar.solarweatherbackend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Solar weather station backend REST API")
                        .description("This API exposes operations which can be" +
                                " used by ECOWitt compatible weather stations" +
                                " (such as Steinberg Systems SBS-WS-500) to post" +
                                " their data in the Ecowitt protocol format")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Fredrik Möller")
                                .url("https://github.com/afmoller/solar")));
    }
}
