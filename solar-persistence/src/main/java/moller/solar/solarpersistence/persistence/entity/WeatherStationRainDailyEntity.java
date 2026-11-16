package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "WEATHER_STATION_RAIN_DAILY")
public class WeatherStationRainDailyEntity {

    public static final String ID_PK = "ID_PK";
    public static final String DATEUTC = "DATEUTC";
    public static final String DAILYRAININ = "DAILYRAININ";
    public static final String YEARLYRAININ = "YEARLYRAININ";
    public static final String MONTHLYRAININ = "MONTHLYRAININ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_station_rain_daily_generator")
    @SequenceGenerator(name = "weather_station_rain_daily_generator", sequenceName = "WEATHER_STATION_RAIN_DAILY_SEQ", allocationSize = 1)
    @Column(name = ID_PK)
    private Integer id;

    @Column(name = DATEUTC)
    private OffsetDateTime dateutc;

    @Column(name = DAILYRAININ)
    private Float dailyrainin;

    @Column(name = YEARLYRAININ)
    private Float yearlyrainin;

    @Column(name = MONTHLYRAININ)
    private Float monthlyrainin;

    /**
     * Needed for Hibernate
     */
    public WeatherStationRainDailyEntity() {
    }

    public Integer getId() {
        return id;
    }

    public OffsetDateTime getDateutc() {
        return dateutc;
    }

    public Float getDailyrainin() {
        return dailyrainin;
    }

    public Float getYearlyrainin() {
        return yearlyrainin;
    }

    public Float getMonthlyrainin() {
        return monthlyrainin;
    }

    public void setDateutc(OffsetDateTime dateutc) {
        this.dateutc = dateutc;
    }

    public void setDailyrainin(Float dailyrainin) {
        this.dailyrainin = dailyrainin;
    }

    public void setYearlyrainin(Float yearlyrainin) {
        this.yearlyrainin = yearlyrainin;
    }

    public void setMonthlyrainin(Float monthlyrainin) {
        this.monthlyrainin = monthlyrainin;
    }
}