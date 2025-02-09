package moller.solar.solarpersistence.controller;

import moller.openapi.persistence.weather.api.WeatherStationControllerApi;
import moller.openapi.persistence.weather.model.WeatherStationDataEntry;
import moller.solar.solarpersistence.service.WeatherStationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherStationController implements WeatherStationControllerApi {

    private final WeatherStationService weatherStationService;

    public WeatherStationController(
            WeatherStationService weatherStationService) {

        this.weatherStationService = weatherStationService;
    }


    @Override
    public ResponseEntity<Void> createWeatherStationDataEntry(WeatherStationDataEntry weatherStationDataEntry) {
        WeatherStationDataEntry createdWeatherStationDataEntry = weatherStationService.createWeaterStationEntry(weatherStationDataEntry);


        return ResponseEntity.noContent().build();
    }
}
