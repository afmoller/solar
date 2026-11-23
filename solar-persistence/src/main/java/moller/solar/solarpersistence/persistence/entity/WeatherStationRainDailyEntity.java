package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "WEATHER_STATION_RAIN_DAILY")
public class WeatherStationRainDailyEntity {

    private static final String ID = "ID";
    private static final String YEAR = "YEAR";
    private static final String MONTH = "MONTH";
    private static final String DATEUTC = "DATEUTC";
    private static final String DAILYRAININ = "DAILYRAININ";
    private static final String YEARLYRAININ = "YEARLYRAININ";
    private static final String MONTHLYRAININ = "MONTHLYRAININ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_station_rain_daily_generator")
    @SequenceGenerator(name = "weather_station_rain_daily_generator", sequenceName = "WEATHER_STATION_RAIN_DAILY_SEQ", allocationSize = 1)
    @Column(name = ID)
    private Integer id;

    @Column(name = YEAR)
    private Integer year;

    @Column(name = MONTH)
    private Integer month;

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

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
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

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
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