package moller.solar.solarpersistence.controller;

import moller.openapi.persistence.solar.api.WeatherControllerApi;
import moller.openapi.persistence.solar.model.WeatherDataEntry;
import moller.solar.solarpersistence.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WeatherController implements WeatherControllerApi {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public ResponseEntity<WeatherDataEntry> getCurrentWeatherEntry() {
        return ResponseEntity.of(Optional.of(weatherService.getCurrentWeatherEntry()));
    }
}
