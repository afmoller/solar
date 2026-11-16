package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.persistence.entity.WeatherStationRainYearlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStationRainYearlyRepository extends JpaRepository<WeatherStationRainYearlyEntity, Integer> {

}