package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "WEATHER_STATION_RAIN_YEARLY")
public class WeatherStationRainYearlyEntity {

    public static final String ID_PK = "ID_PK";
    public static final String DATEUTC = "DATEUTC";
    public static final String YEARLYRAININ = "YEARLYRAININ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_station_rain_yearly_generator")
    @SequenceGenerator(name = "weather_station_rain_yearly_generator", sequenceName = "WEATHER_STATION_RAIN_YEARLY_SEQ", allocationSize = 1)
    @Column(name = ID_PK)
    private Integer id;

    @Column(name = DATEUTC)
    private OffsetDateTime dateutc;

    @Column(name = YEARLYRAININ)
    private Float yearlyrainin;

    /**
     * Needed for Hibernate
     */
    public WeatherStationRainYearlyEntity() {
    }

    public Integer getId() {
        return id;
    }

    public OffsetDateTime getDateutc() {
        return dateutc;
    }

    public Float getYearlyrainin() {
        return yearlyrainin;
    }

    public void setDateutc(OffsetDateTime dateutc) {
        this.dateutc = dateutc;
    }

    public void setYearlyrainin(Float yearlyrainin) {
        this.yearlyrainin = yearlyrainin;
    }
}