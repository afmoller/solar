package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.WeatherStationRainMonthlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStationRainMonthlyRepository extends JpaRepository<WeatherStationRainMonthlyEntity, Integer> {

}