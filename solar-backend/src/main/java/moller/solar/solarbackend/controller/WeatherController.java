package moller.solar.solarbackend.controller;

import moller.solarpersistence.openapi.client.api.WeatherControllerApi;
import moller.solarpersistence.openapi.client.model.WeatherDataEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WeatherController extends AbstractV1BaseController {

    private final WeatherControllerApi weatherControllerApi;

    public WeatherController(WeatherControllerApi weatherControllerApi) {
        this.weatherControllerApi = weatherControllerApi;
    }

    @GetMapping(value = "/weather/current")
    public ResponseEntity<WeatherDataEntry> getCurrentWeatherEntry() {
        return ResponseEntity.of(Optional.of(weatherControllerApi.getCurrentWeatherEntry()));
    }
}
