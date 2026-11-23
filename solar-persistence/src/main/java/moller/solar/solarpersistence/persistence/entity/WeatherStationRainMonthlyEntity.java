package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "WEATHER_STATION_RAIN_MONTHLY")
public class WeatherStationRainMonthlyEntity {

    private static final String ID = "ID";
    private static final String YEAR = "YEAR";
    private static final String DATEUTC = "DATEUTC";
    private static final String YEARLYRAININ = "YEARLYRAININ";
    private static final String MONTHLYRAININ = "MONTHLYRAININ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_station_rain_monthly_generator")
    @SequenceGenerator(name = "weather_station_rain_monthly_generator", sequenceName = "WEATHER_STATION_RAIN_MONTHLY_SEQ", allocationSize = 1)
    @Column(name = ID)
    private Integer id;

    @Column(name = YEAR)
    private Integer year;

    @Column(name = DATEUTC)
    private OffsetDateTime dateutc;

    @Column(name = YEARLYRAININ)
    private Float yearlyrainin;

    @Column(name = MONTHLYRAININ)
    private Float monthlyrainin;

    /**
     * Needed for Hibernate
     */
    public WeatherStationRainMonthlyEntity() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public OffsetDateTime getDateutc() {
        return dateutc;
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

    public void setDateutc(OffsetDateTime dateutc) {
        this.dateutc = dateutc;
    }

    public void setYearlyrainin(Float yearlyrainin) {
        this.yearlyrainin = yearlyrainin;
    }

    public void setMonthlyrainin(Float monthlyrainin) {
        this.monthlyrainin = monthlyrainin;
    }
}