package moller.solar.solarweatherbackend.controller;

import moller.solarpersistence.weather.openapi.client.api.WeatherStationControllerApi;
import moller.solarpersistence.weather.openapi.client.model.WeatherStationDataEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

@RestController
@RequestMapping("/api/v1/")
public class WeatherStationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherStationController.class);

    public static final String UV = "uv";
    public static final String FREQ = "freq";
    public static final String HEAP = "heap";
    public static final String MODEL = "model";
    public static final String TEMPF = "tempf";
    public static final String DATEUTC = "dateutc";
    public static final String PASSKEY = "PASSKEY";
    public static final String RUNTIME = "runtime";
    public static final String TEMPINF = "tempinf";
    public static final String WINDDIR = "winddir";
    public static final String HUMIDITY = "humidity";
    public static final String INTERVAL = "interval";
    public static final String BAROMABSIN = "baromabsin";
    public static final String BAROMRELIN = "baromrelin";
    public static final String HUMIDITYIN = "humidityin";
    public static final String RAINRATEIN = "rainratein";
    public static final String WH_25_BATT = "wh25batt";
    public static final String WH_65_BATT = "wh65batt";
    public static final String DAILYRAININ = "dailyrainin";
    public static final String EVENTRAININ = "eventrainin";
    public static final String STATIONTYPE = "stationtype";
    public static final String WINDGUSTMPH = "windgustmph";
    public static final String HOURLYRAININ = "hourlyrainin";
    public static final String MAXDAILYGUST = "maxdailygust";
    public static final String WEEKLYRAININ = "weeklyrainin";
    public static final String WINDSPEEDMPH = "windspeedmph";
    public static final String YEARLYRAININ = "yearlyrainin";
    public static final String MONTHLYRAININ = "monthlyrainin";
    public static final String SOLARRADIATION = "solarradiation";

    private final WeatherStationControllerApi weatherStationControllerApi;

    @Value("${dry-run}")
    private boolean dryRun;

    public WeatherStationController(WeatherStationControllerApi weatherStationControllerApi) {
        this.weatherStationControllerApi = weatherStationControllerApi;
    }

    @PostMapping(value = "/weather-station-data-entries", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> createWeatherStationDataEntry(@RequestParam MultiValueMap<String,String> paramMap) {

        try {
            WeatherStationDataEntry weatherStationDataEntry = buildWeatherStationDataEntry(paramMap);

            if (dryRun) {
                LOGGER.info(weatherStationDataEntry.toString());
            } else {
                weatherStationControllerApi.createWeatherStationDataEntry(weatherStationDataEntry);
            }
        } catch (Exception ex) {
            LOGGER.error("An {} could not be created due to the following error: {}", WeatherStationDataEntry.class.getSimpleName(), ex.toString());
        }

        // Regardless of an error or not, we always send a Http 201 back to the
        // weather station, since the weather station is the master of the data
        // and if the code here has a problem with it, this code has to adapt.
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private WeatherStationDataEntry buildWeatherStationDataEntry(MultiValueMap<String,String> paramMap) {
        try {
            return new WeatherStationDataEntry.Builder()
                    .freq(getValueOrEmptyString(paramMap, FREQ))
                    .model(getValueOrEmptyString(paramMap, MODEL))
                    .uv(toInt(getValueOrEmptyString(paramMap, UV)))
                    .passkey(getValueOrEmptyString(paramMap, PASSKEY))
                    .heap(toInt(getValueOrEmptyString(paramMap, HEAP)))
                    .tempf(toFloat(getValueOrEmptyString(paramMap, TEMPF)))
                    .runtime(toInt(getValueOrEmptyString(paramMap, RUNTIME)))
                    .winddir(toInt(getValueOrEmptyString(paramMap, WINDDIR)))
                    .stationtype(getValueOrEmptyString(paramMap, STATIONTYPE))
                    .humidity(toInt(getValueOrEmptyString(paramMap, HUMIDITY)))
                    .interval(toInt(getValueOrEmptyString(paramMap, INTERVAL)))
                    .tempinf(toFloat(getValueOrEmptyString(paramMap, TEMPINF)))
                    .wh25batt(toInt(getValueOrEmptyString(paramMap, WH_25_BATT)))
                    .wh65batt(toInt(getValueOrEmptyString(paramMap, WH_65_BATT)))
                    .humidityin(toInt(getValueOrEmptyString(paramMap, HUMIDITYIN)))
                    .baromrelin(toFloat(getValueOrEmptyString(paramMap, BAROMRELIN)))
                    .baromabsin(toFloat(getValueOrEmptyString(paramMap, BAROMABSIN)))
                    .rainratein(toFloat(getValueOrEmptyString(paramMap, RAINRATEIN)))
                    .dailyrainin(toFloat(getValueOrEmptyString(paramMap, DAILYRAININ)))
                    .eventrainin(toFloat(getValueOrEmptyString(paramMap, EVENTRAININ)))
                    .windgustmph(toFloat(getValueOrEmptyString(paramMap, WINDGUSTMPH)))
                    .hourlyrainin(toFloat(getValueOrEmptyString(paramMap, HOURLYRAININ)))
                    .maxdailygust(toFloat(getValueOrEmptyString(paramMap, MAXDAILYGUST)))
                    .weeklyrainin(toFloat(getValueOrEmptyString(paramMap, WEEKLYRAININ)))
                    .windspeedmph(toFloat(getValueOrEmptyString(paramMap, WINDSPEEDMPH)))
                    .yearlyrainin(toFloat(getValueOrEmptyString(paramMap, YEARLYRAININ)))
                    .monthlyrainin(toFloat(getValueOrEmptyString(paramMap, MONTHLYRAININ)))
                    .solarradiation(toFloat(getValueOrEmptyString(paramMap, SOLARRADIATION)))
                    .dateutc(OffsetDateTime.of(LocalDateTime.parse(getValueOrEmptyString(paramMap, DATEUTC), LOCAL_DATE_TIME), ZoneOffset.UTC))
                    .build();
        } catch (Exception exception) {
            LOGGER.error("An instance of: {} could not be constructed from the following content: {} due to the exception being thrown: {}"
                    , WeatherStationDataEntry.class.getSimpleName()
                    , paramMap
                    , exception.toString());

            throw exception;
        }
    }

    private String getValueOrEmptyString(MultiValueMap<String,String> paramMap, String key) {
        List<String> strings = paramMap.get(key);
        if (strings == null) {
            LOGGER.error("The key: {} is missing in the paramMap: {}"
                    , key
                    , paramMap);

            return "";
        }

        String value = strings.get(0);
        if (value == null) {
            LOGGER.error("No value found for the key: {} in the paramMap: {}"
                    , key
                    , paramMap);

            return "";
        }

        return value;
    }

    private int toInt(String heap) {
        return Integer.parseInt(heap);
    }

    private float toFloat(String floatValueAsString) {
        return Float.parseFloat(floatValueAsString);
    }

    /**
     * Custom Date time formatter. Like ISO-8601 but with the 'T' separator
     * between date and time replaces with an ' ' (whitespace)
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME;
    static {
        LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME)
                .toFormatter();
    }
}
