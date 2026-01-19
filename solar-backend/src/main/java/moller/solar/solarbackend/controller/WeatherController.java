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
    public ResponseEntity<TimeAndWeatherValues> getWeatherByDateEntries(@RequestParam LocalDate selectedDate) {
        List<WeatherDataEntryDateDto> weatherByDateEntries = weatherControllerApi.getWeatherByDateEntries(selectedDate);

        Map<LocalTime, WeatherDataEntryDateDto> serverEntriesByTime = weatherByDateEntries
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getDateUtc().toLocalTime().withSecond(0).withNano(0),
                        entry -> entry,
                        (objectWithSameKey, anotherObjectWithSameKey) -> anotherObjectWithSameKey));

        LocalTime midnightStartOfDay = LocalTime.MIN;
        LocalTime midnightEndOfDayMinusFourMinutes = LocalTime.MAX.withSecond(0).withNano(0).minusMinutes(4);

        TimeAndWeatherValues timeAndWeatherValues = new TimeAndWeatherValues();

        LocalTime queryLocalTime = midnightStartOfDay;

        int index = 0;

        while (true) {
            WeatherDataEntryDateDto weatherDataEntryDateDto = serverEntriesByTime.get(queryLocalTime);

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

            if (queryLocalTime.equals(midnightEndOfDayMinusFourMinutes)) {
                break;
            } else {
                queryLocalTime = queryLocalTime.plusMinutes(5);
            }

            index++;
        }

        return ResponseEntity.of(Optional.of(timeAndWeatherValues));
    }
}
