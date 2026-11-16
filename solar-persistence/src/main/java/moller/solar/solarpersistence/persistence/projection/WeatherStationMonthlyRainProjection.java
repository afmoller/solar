package moller.solar.solarpersistence.persistence.projection;

import java.math.BigDecimal;
import java.time.Instant;

public record WeatherStationMonthlyRainProjection(
        Instant dateutc,
        BigDecimal yearlyrainin,
        BigDecimal monthlyrainin) {
}
