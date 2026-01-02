package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.WeatherStationEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStationEntryEntity, Integer> {

    @Query(value = "SELECT w FROM WeatherStationEntryEntity w WHERE w.id =  (SELECT MAX(wmax.id) FROM WeatherStationEntryEntity wmax)")
    WeatherStationEntryEntity findCurrentWeatherEntry();

    @Query(value = "SELECT w FROM WeatherStationEntryEntity w WHERE w.dateutc >=  :dateFrom AND w.dateutc <= :dateTo")
    List<WeatherStationEntryEntity> getWeatherInformationForDate(OffsetDateTime dateFrom, OffsetDateTime dateTo);
}
