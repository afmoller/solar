package moller.solar.solarpersistence.service;

import moller.openapi.persistence.solar.model.WeatherDataEntry;
import moller.solar.solarpersistence.mapper.WeatherMapper;
import moller.solar.solarpersistence.persistence.entity.WeatherStationEntryEntity;
import moller.solar.solarpersistence.persistence.repository.WeatherStationRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class WeatherService {

    private final WeatherMapper weatherMapper;
    private final WeatherStationRepository weatherStationRepository;

    public WeatherService(
            WeatherMapper weatherMapper,
            WeatherStationRepository weatherStationRepository) {

        this.weatherMapper = weatherMapper;
        this.weatherStationRepository = weatherStationRepository;
    }

    public WeatherDataEntry getCurrentWeatherEntry() {
        WeatherStationEntryEntity currentWeatherEntry = weatherStationRepository.findCurrentWeatherEntry();
        return weatherMapper.map(currentWeatherEntry);
    }

    public List<WeatherDataEntry> getWeatherByDateEntries(OffsetDateTime dateFrom, OffsetDateTime dateTo) {
        List<WeatherStationEntryEntity> weatherInformationForDate = weatherStationRepository.getWeatherInformationForDate(dateFrom, dateTo);
        return weatherMapper.map(weatherInformationForDate);
    }
}
