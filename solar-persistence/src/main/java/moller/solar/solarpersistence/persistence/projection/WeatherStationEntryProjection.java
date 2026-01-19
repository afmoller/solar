package moller.solar.solarpersistence.persistence.projection;

import java.time.OffsetDateTime;

public record WeatherStationEntryProjection(
        Integer id,
        Integer uv,
        Float tempf,
        OffsetDateTime dateutc,
        Float tempinf,
        Integer winddir,
        Integer humidity,
        Float baromabsin,
        Integer humidityin,
        Float rainratein,
        Float dailyrainin,
        Float eventrainin,
        Float windgustmph,
        Float hourlyrainin,
        Float maxdailygust,
        Float windspeedmph,
        Float solarradiation) {
}
