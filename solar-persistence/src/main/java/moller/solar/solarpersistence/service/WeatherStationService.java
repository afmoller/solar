package moller.solar.solarpersistence.service;

import moller.openapi.persistence.weather.model.WeatherStationDataEntry;
import moller.solar.solarpersistence.mapper.WeatherStationMapper;
import moller.solar.solarpersistence.persistence.entity.WeatherStationEntryEntity;
import moller.solar.solarpersistence.persistence.repository.WeatherStationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeatherStationService {

    private final WeatherStationMapper weatherStationMapper;
    private final WeatherStationRepository weatherStationRepository;

    public WeatherStationService(
            WeatherStationMapper weatherStationMapper,
            WeatherStationRepository weatherStationRepository) {

        this.weatherStationMapper = weatherStationMapper;
        this.weatherStationRepository = weatherStationRepository;
    }

    @Transactional
    public WeatherStationDataEntry createWeatherStationEntry(WeatherStationDataEntry weatherStationDataEntry) {
        WeatherStationEntryEntity savedWeatherStationEntryEntity = weatherStationRepository.save(weatherStationMapper.map(weatherStationDataEntry));

        return weatherStationMapper.map(savedWeatherStationEntryEntity);
    }
}
