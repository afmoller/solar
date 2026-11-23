package moller.solar.solarpersistence.cron;

import moller.solar.solarpersistence.persistence.entity.WeatherStationRainDailyEntity;
import moller.solar.solarpersistence.persistence.projection.WeatherStationDailyRainProjection;
import moller.solar.solarpersistence.persistence.repository.WeatherStationRainDailyRepository;
import moller.solar.solarpersistence.persistence.repository.WeatherStationRainMonthlyRepository;
import moller.solar.solarpersistence.persistence.repository.WeatherStationRainYearlyRepository;
import moller.solar.solarpersistence.persistence.repository.WeatherStationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class WeatherStationCrons {

    private final WeatherStationRepository weatherStationRepository;
    private final WeatherStationRainDailyRepository weatherStationRainDailyRepository;
    private final WeatherStationRainYearlyRepository weatherStationRainYearlyRepository;
    private final WeatherStationRainMonthlyRepository weatherStationRainMonthlyRepository;

    public WeatherStationCrons(
            WeatherStationRepository weatherStationRepository,
            WeatherStationRainDailyRepository weatherStationRainDailyRepository,
            WeatherStationRainYearlyRepository weatherStationRainYearlyRepository,
            WeatherStationRainMonthlyRepository weatherStationRainMonthlyRepository) {

        this.weatherStationRepository = weatherStationRepository;
        this.weatherStationRainDailyRepository = weatherStationRainDailyRepository;
        this.weatherStationRainYearlyRepository = weatherStationRainYearlyRepository;
        this.weatherStationRainMonthlyRepository = weatherStationRainMonthlyRepository;
    }

    @Scheduled(cron = "${cron.latest-weather-entries-by-day}")
    public void getLatestWeatherEntriesByDay() {
        Optional<WeatherStationRainDailyEntity> latestWeatherStationRainDailyEntityEntryOptional = weatherStationRainDailyRepository.findFirstByOrderByDateutcDesc();

        if (latestWeatherStationRainDailyEntityEntryOptional.isEmpty()) {
            persistAsWeatherStationRainDailyEntities(weatherStationRepository.getLatestWeatherEntriesByDay());
            return;
        }

        List<WeatherStationDailyRainProjection> latestWeatherEntriesByDay = weatherStationRepository.getLatestWeatherEntriesByDay(
                latestWeatherStationRainDailyEntityEntryOptional.get().getDateutc(),
                OffsetDateTime.now());

        if (latestWeatherEntriesByDay.isEmpty()) {
            return;
        }

        WeatherStationDailyRainProjection firstLatestWeatherEntriesByDay = latestWeatherEntriesByDay.getFirst();

        OffsetDateTime offsetDateTimeOfLatestWeatherEntryByDay = OffsetDateTime.ofInstant(firstLatestWeatherEntriesByDay.dateutc(), ZoneOffset.UTC);

        WeatherStationRainDailyEntity latestWeatherStationRainDailyEntity = latestWeatherStationRainDailyEntityEntryOptional.get();

        if (!latestWeatherStationRainDailyEntity.getDateutc().truncatedTo(ChronoUnit.DAYS).isEqual(offsetDateTimeOfLatestWeatherEntryByDay.truncatedTo(ChronoUnit.DAYS))) {
            persistAsWeatherStationRainDailyEntities(latestWeatherEntriesByDay);
        } else if (latestWeatherStationRainDailyEntity.getDateutc().isEqual(offsetDateTimeOfLatestWeatherEntryByDay)) {
            if (latestWeatherEntriesByDay.size() > 1) {
                persistAsWeatherStationRainDailyEntities(latestWeatherEntriesByDay.subList(1, latestWeatherEntriesByDay.size()));
            }

        } else if (latestWeatherStationRainDailyEntity.getDateutc().isBefore(offsetDateTimeOfLatestWeatherEntryByDay)) {
            weatherStationRainDailyRepository.save(
                    populateEntity(
                            offsetDateTimeOfLatestWeatherEntryByDay,
                            latestWeatherStationRainDailyEntity,
                            firstLatestWeatherEntriesByDay));

            if (latestWeatherEntriesByDay.size() > 1) {
                persistAsWeatherStationRainDailyEntities(latestWeatherEntriesByDay.subList(1, latestWeatherEntriesByDay.size()));
            }
        }
    }

    @Scheduled(cron = "${cron.latest-weather-entries-by-month}")
    public void getLatestWeatherEntriesByMonth() {

    }

    @Scheduled(cron = "${cron.latest-weather-entries-by-year}")
    public void getLatestWeatherEntriesByYear() {

    }

    private void persistAsWeatherStationRainDailyEntities(List<WeatherStationDailyRainProjection> latestWeatherEntriesByDay) {
        weatherStationRainDailyRepository.saveAll(
                latestWeatherEntriesByDay
                        .stream()
                        .map(weatherStationDailyRainProjection -> populateEntity(
                                OffsetDateTime.ofInstant(weatherStationDailyRainProjection.dateutc(), ZoneOffset.UTC),
                                new WeatherStationRainDailyEntity(),
                                weatherStationDailyRainProjection))
                        .toList());
    }

    private WeatherStationRainDailyEntity populateEntity(
            OffsetDateTime offsetDateTime,
            WeatherStationRainDailyEntity weatherStationRainDailyEntity,
            WeatherStationDailyRainProjection weatherStationDailyRainProjection) {

        weatherStationRainDailyEntity.setDateutc(offsetDateTime);
        weatherStationRainDailyEntity.setDailyrainin(weatherStationDailyRainProjection.dailyrainin().floatValue());
        weatherStationRainDailyEntity.setYearlyrainin(weatherStationDailyRainProjection.yearlyrainin().floatValue());
        weatherStationRainDailyEntity.setMonthlyrainin(weatherStationDailyRainProjection.monthlyrainin().floatValue());
        weatherStationRainDailyEntity.setYear(offsetDateTime.getYear());
        weatherStationRainDailyEntity.setMonth(offsetDateTime.getMonthValue());

        return weatherStationRainDailyEntity;
    }
}
