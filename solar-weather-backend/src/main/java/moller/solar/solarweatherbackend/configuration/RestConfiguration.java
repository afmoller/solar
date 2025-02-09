package moller.solar.solarweatherbackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import moller.solarpersistence.weather.openapi.client.api.WeatherStationControllerApi;
import moller.solarpersistence.weather.openapi.client.ApiClient;

@Configuration
public class RestConfiguration {

    @Value("${rest.solar-persistence.base-path}")
    private String basePath;

    private final RestClient.Builder builder;

    public RestConfiguration(RestClient.Builder builder) {
        this.builder = builder;
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient(restClient(builder));
        apiClient.setBasePath(basePath);

        return apiClient;
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public WeatherStationControllerApi weatherStationControllerApi() {
        return new WeatherStationControllerApi(apiClient());
    }
}
