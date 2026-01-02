package moller.solar.solarpersistence.mapper;

import moller.openapi.persistence.solar.model.WeatherDataEntry;
import moller.solar.solarpersistence.persistence.entity.WeatherStationEntryEntity;
import org.mapstruct.Mapper;

import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public abstract class WeatherMapper {

    public WeatherDataEntry map(WeatherStationEntryEntity weatherStationEntryEntity) {
        return new WeatherDataEntry()
                .uv(weatherStationEntryEntity.getUv())
                .freq(weatherStationEntryEntity.getFreq())
                .heap(weatherStationEntryEntity.getHeap())
                .model(weatherStationEntryEntity.getModel())
                .passkey(weatherStationEntryEntity.getPasskey())
                .runtime(weatherStationEntryEntity.getRuntime())
                .interval(weatherStationEntryEntity.getInterval())
                .wh25Batt(weatherStationEntryEntity.getWh25batt())
                .wh65Batt(weatherStationEntryEntity.getWh65batt())
                .windDirection(weatherStationEntryEntity.getWinddir())
                .stationType(weatherStationEntryEntity.getStationtype())
                .humidityOutdoor(weatherStationEntryEntity.getHumidity())
                .humidityIndoor(weatherStationEntryEntity.getHumidityin())
                .solarRadiation(weatherStationEntryEntity.getSolarradiation())
                .windGustInMeterPerSecond(mphToMs(weatherStationEntryEntity.getWindgustmph()))
                .windSpeedInMeterPerSecond(mphToMs(weatherStationEntryEntity.getWindspeedmph()))
                .maxDailyGustInMeterPerSecond(mphToMs(weatherStationEntryEntity.getMaxdailygust()))
                .temperatureOutdoorInCelsius(fahrenheitToCelsius(weatherStationEntryEntity.getTempf()))
                .temperatureIndoorInCelcius(fahrenheitToCelsius(weatherStationEntryEntity.getTempinf()))
                .eventRainInMillimeters(inchesToMillimeters(weatherStationEntryEntity.getEventrainin()))
                .hourlyRainInMillimeters(inchesToMillimeters(weatherStationEntryEntity.getHourlyrainin()))
                .dailyRainInMillimeters(inchesToMillimeters(weatherStationEntryEntity.getDailyrainin()))
                .monthlyRainInMillimeters(inchesToMillimeters(weatherStationEntryEntity.getMonthlyrainin()))
                .yearlyRainInMillimeters(inchesToMillimeters(weatherStationEntryEntity.getYearlyrainin()))
                .rainRateInMillimetersPerHour(inchesToMillimeters(weatherStationEntryEntity.getRainratein()))
                .weeklyRainInMillimeters(inchesToMillimeters(weatherStationEntryEntity.getWeeklyrainin()))
                .baromAbsoluteIndoor(inchMercuryToHectoPascal(weatherStationEntryEntity.getBaromabsin()))
                .baromRelativeIndoor(inchMercuryToHectoPascal(weatherStationEntryEntity.getBaromrelin()))
                .dateUtc(weatherStationEntryEntity.getDateutc().atZoneSameInstant(ZoneId.of("Europe/Paris")).toLocalDateTime());
    }

    public List<WeatherDataEntry> map(List<WeatherStationEntryEntity> weatherInformationForDate) {
        return weatherInformationForDate
                .stream()
                .map(this::map)
                .toList();
    }

    private Float mphToMs(Float mph) {
        return mph / 2.237F;
    }

    private Float inchesToMillimeters(Float inches) {
        return inches * 25.4F;
    }

    private Float fahrenheitToCelsius(Float fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    private Float inchMercuryToHectoPascal(Float inches) {
        return inches * 33.86389F;
    }
}
