package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.timeandvalues.TimeAndWeatherValues;
import moller.solarpersistence.openapi.client.api.WeatherControllerApi;
import moller.solarpersistence.openapi.client.model.WeatherDataEntry;
import moller.solarpersistence.openapi.client.model.WeatherDataEntryDateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/weather/weatherbydate")
    public ResponseEntity<TimeAndWeatherValues> getWeatherByDateEntries(
            @RequestParam LocalDate selectedDateFrom,
            @RequestParam LocalDate selectedDateTo) {

        List<WeatherDataEntryDateDto> weatherByDateEntries = weatherControllerApi.getWeatherByDateEntries(selectedDateFrom, selectedDateTo);

        Map<LocalDateTime, WeatherDataEntryDateDto> serverEntriesByDateTime = weatherByDateEntries
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getDateUtc().withSecond(0).withNano(0),
                        entry -> entry,
                        (objectWithSameKey, anotherObjectWithSameKey) -> anotherObjectWithSameKey));

        LocalDateTime midnightStartOfSelectedDateFrom = LocalDateTime.of(selectedDateFrom, LocalTime.MIN);
        LocalDateTime queryEndDateTime = getQueryEndtime(selectedDateTo);

        TimeAndWeatherValues timeAndWeatherValues = new TimeAndWeatherValues(midnightStartOfSelectedDateFrom, queryEndDateTime);

        LocalDateTime queryLocalDateTime = midnightStartOfSelectedDateFrom;

        int index = 0;

        while (true) {
            WeatherDataEntryDateDto weatherDataEntryDateDto = serverEntriesByDateTime.get(queryLocalDateTime);

            if (weatherDataEntryDateDto == null) {
                // sometimes the data delivery from the weather station is not
                // always once per minute, if so, then go one minute back and
                // take that value.
                weatherDataEntryDateDto = serverEntriesByDateTime.get(queryLocalDateTime.minusMinutes(1));
            }

            if (weatherDataEntryDateDto != null) {
                timeAndWeatherValues.setValueToBaromAbsoluteIndoorList(index, weatherDataEntryDateDto.getBaromAbsoluteIndoor());
                timeAndWeatherValues.setValueToUvList(index, weatherDataEntryDateDto.getUv());
                timeAndWeatherValues.setValueToHumidityIndoorList(index, weatherDataEntryDateDto.getHumidityIndoor());
                timeAndWeatherValues.setValueToHumidityOutdoorList(index, weatherDataEntryDateDto.getHumidityOutdoor());
                timeAndWeatherValues.setValueToDailyRainInMillimetersList(index, weatherDataEntryDateDto.getDailyRainInMillimeters());
                timeAndWeatherValues.setValueToHourlyRainInMillimetersList(index, weatherDataEntryDateDto.getHourlyRainInMillimeters());
                timeAndWeatherValues.setValueToMaxDailyGustInMeterPerSecondList(index, weatherDataEntryDateDto.getMaxDailyGustInMeterPerSecond());
                timeAndWeatherValues.setValueToRainRateInMillimetersPerHourList(index, weatherDataEntryDateDto.getRainRateInMillimetersPerHour());
                timeAndWeatherValues.setValueToSolarRadiationList(index, weatherDataEntryDateDto.getSolarRadiation());
                timeAndWeatherValues.setValueToTemperatureIndoorInCelsiusList(index, weatherDataEntryDateDto.getTemperatureIndoorInCelcius());
                timeAndWeatherValues.setValueToTemperatureOutdoorInCelsiusList(index, weatherDataEntryDateDto.getTemperatureOutdoorInCelsius());
                timeAndWeatherValues.setValueToWindSpeedInMeterPerSecondsList(index, weatherDataEntryDateDto.getWindSpeedInMeterPerSecond());
            }

            if (queryLocalDateTime.equals(queryEndDateTime)) {
                break;
            } else {
                queryLocalDateTime = queryLocalDateTime.plusMinutes(5);
            }

            index++;
        }

        return ResponseEntity.of(Optional.of(timeAndWeatherValues));
    }

    private LocalDateTime getQueryEndtime(LocalDate selectedDate) {

        LocalDateTime queryEndDateTime;

        if (selectedDate.isEqual(LocalDate.now())) {
            LocalDateTime now = LocalDateTime.of(selectedDate, LocalTime.now().withSecond(0).withNano(0));
            queryEndDateTime = now.minusMinutes(now.getMinute() % 5);
        } else {
            queryEndDateTime = LocalDateTime.of(selectedDate, LocalTime.MAX.withSecond(0).withNano(0).minusMinutes(4));
        }

        return queryEndDateTime;
    }
}
