package moller.solar.solarpersistence.persistence.projection;

import java.math.BigDecimal;
import java.time.Instant;

public record WeatherStationYearlyRainProjection(
        Instant dateutc,
        BigDecimal yearlyrainin) {
}