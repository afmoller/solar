package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.WeatherStationEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStationEntryEntity, Integer> {

    @Query(value = "SELECT w FROM WeatherStationEntryEntity w WHERE w.id =  (SELECT MAX(wmax.id) FROM WeatherStationEntryEntity wmax)")
    WeatherStationEntryEntity findCurrentWeatherEntry();
}
