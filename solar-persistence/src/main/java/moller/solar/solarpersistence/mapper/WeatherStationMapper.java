package moller.solar.solarpersistence.mapper;

import moller.openapi.persistence.weather.model.WeatherStationDataEntry;
import moller.solar.solarpersistence.persistence.entity.WeatherStationEntryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface WeatherStationMapper {

    WeatherStationDataEntry map(WeatherStationEntryEntity weatherStationEntryEntity);

    WeatherStationEntryEntity map( WeatherStationDataEntry weatherStationDataEntry);
}
