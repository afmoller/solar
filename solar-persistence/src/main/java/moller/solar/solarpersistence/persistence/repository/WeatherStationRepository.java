package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.WeatherStationEntryEntity;
import moller.solar.solarpersistence.persistence.projection.WeatherStationDailyRainProjection;
import moller.solar.solarpersistence.persistence.projection.WeatherStationEntryProjection;
import moller.solar.solarpersistence.persistence.projection.WeatherStationMonthlyRainProjection;
import moller.solar.solarpersistence.persistence.projection.WeatherStationYearlyRainProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStationEntryEntity, Integer> {

    @Query(value = "SELECT w FROM WeatherStationEntryEntity w WHERE w.id = (SELECT MAX(wmax.id) FROM WeatherStationEntryEntity wmax)")
    WeatherStationEntryEntity findCurrentWeatherEntry();

    @Query(value = "SELECT w FROM WeatherStationEntryEntity w WHERE w.dateutc >= :dateFrom AND w.dateutc <= :dateTo")
    List<WeatherStationEntryProjection> getWeatherInformationForDate(OffsetDateTime dateFrom, OffsetDateTime dateTo);

    @NativeQuery(value = "SELECT DISTINCT ON (date_trunc('year', dateutc)) dateutc, yearlyrainin FROM public.weather_station w ORDER BY date_trunc('year', dateutc), dateutc DESC;")
    List<WeatherStationYearlyRainProjection> getLatestWeatherEntriesByYear();

    @NativeQuery(value = "SELECT DISTINCT ON (date_trunc('month', dateutc)) dateutc, yearlyrainin, monthlyrainin FROM public.weather_station w ORDER BY date_trunc('month', dateutc), dateutc DESC;")
    List<WeatherStationMonthlyRainProjection> getLatestWeatherEntriesByMonth();

    @NativeQuery(value = "SELECT DISTINCT ON (date_trunc('day', dateutc)) dateutc, yearlyrainin, monthlyrainin, dailyrainin FROM public.weather_station w ORDER BY date_trunc('day', dateutc), dateutc DESC;")
    List<WeatherStationDailyRainProjection> getLatestWeatherEntriesByDay();

    @Query(value = "SELECT DISTINCT ON (date_trunc('day', dateutc)) dateutc, yearlyrainin, monthlyrainin, dailyrainin FROM public.weather_station w WHERE w.dateutc >= :dateFrom AND w.dateutc <= :dateTo ORDER BY date_trunc('day', dateutc), dateutc DESC;", nativeQuery = true)
    List<WeatherStationDailyRainProjection> getLatestWeatherEntriesByDay(OffsetDateTime dateFrom, OffsetDateTime dateTo);
}
