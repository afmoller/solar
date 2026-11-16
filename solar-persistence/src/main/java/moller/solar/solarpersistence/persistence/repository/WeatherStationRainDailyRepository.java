package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.WeatherStationRainDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherStationRainDailyRepository extends JpaRepository<WeatherStationRainDailyEntity, Integer> {

    Optional<WeatherStationRainDailyEntity> findFirstByOrderByDateutcDesc();
}
