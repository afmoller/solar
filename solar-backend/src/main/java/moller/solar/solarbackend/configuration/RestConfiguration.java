package moller.solar.solarbackend.configuration;

import moller.solar.solarbackend.interceptor.AuthorizationHeaderSetterInterceptor;
import moller.solarpersistence.openapi.client.ApiClient;
import moller.solarpersistence.openapi.client.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfiguration {

    private final RestClient.Builder builder;

    public RestConfiguration(RestClient.Builder builder) {
        this.builder = builder;
    }

    @Bean
    public DataExportControllerApi dataExportControllerApi() {
        return new DataExportControllerApi(apiClient());
    }

    @Bean
    public EnergyCostControllerApi energyCostControllerApi() {
        return new EnergyCostControllerApi(apiClient());
    }

    @Bean
    public EnergySaleCompensationControllerApi energySaleCompensationControllerApi() {
        return new EnergySaleCompensationControllerApi(apiClient());
    }

    @Bean
    public ReturnOnInvestmentControllerApi returnOnInvestmentControllerApi() {
        return new ReturnOnInvestmentControllerApi(apiClient());
    }

    @Bean
    public SummaryPerDayControllerApi summaryPerDayControllerApi() {
        return new SummaryPerDayControllerApi(apiClient());
    }

    @Bean
    public CsvControllerApi csvControllerApi() {
        return new CsvControllerApi(apiClient());
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient(restClient(builder));
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.requestInterceptor(new AuthorizationHeaderSetterInterceptor()).build();
    }
}
