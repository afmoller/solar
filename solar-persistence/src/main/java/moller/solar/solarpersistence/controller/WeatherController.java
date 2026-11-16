package moller.solar.solarpersistence.controller;

import moller.openapi.persistence.solar.api.WeatherControllerApi;
import moller.openapi.persistence.solar.model.*;
import moller.solar.solarpersistence.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.util.List;
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

    @Override
    public ResponseEntity<List<WeatherDataEntryDateDto>> getWeatherByDateEntries(
            LocalDate dateFrom,
            LocalDate dateTo) {

        ZoneOffset zoneOffset = ZoneId.of("Europe/Paris").getRules().getOffset(LocalDateTime.now());

        OffsetDateTime dateTimeFrom = OffsetDateTime.of(dateFrom, LocalTime.MIN, zoneOffset);
        OffsetDateTime dateTimeTo = OffsetDateTime.of(dateTo, LocalTime.MAX, zoneOffset);

        return ResponseEntity.of(Optional.of(weatherService.getWeatherByDateEntries(dateTimeFrom, dateTimeTo)));
    }

    @Override
    public ResponseEntity<List<WeatherDataEntryDailyRainDto>> getLatestWeatherEntriesByDay() {
        return ResponseEntity.of(Optional.of(weatherService.getLatestWeatherEntriesByDay()));
    }

    @Override
    public ResponseEntity<List<WeatherDataEntryMonthlyRainDto>> getLatestWeatherEntriesByMonth() {
        return ResponseEntity.of(Optional.of(weatherService.getLatestWeatherEntriesByMonth()));
    }

    @Override
    public ResponseEntity<List<WeatherDataEntryYearlyRainDto>> getLatestWeatherEntriesByYear() {
        return ResponseEntity.of(Optional.of(weatherService.getLatestWeatherEntriesByYear()));
    }
}
