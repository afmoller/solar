package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "WEATHER_STATION")
public class WeatherStationEntryEntity {

    public static final String ID = "ID";
    public static final String UV = "UV";
    public static final String FREQ = "FREQ";
    public static final String HEAP = "HEAP";
    public static final String MODEL = "MODEL";
    public static final String TEMPF = "TEMPF";
    public static final String DATEUTC = "DATEUTC";
    public static final String PASSKEY = "PASSKEY";
    public static final String RUNTIME = "RUNTIME";
    public static final String TEMPINF = "TEMPINF";
    public static final String WINDDIR = "WINDDIR";
    public static final String HUMIDITY = "HUMIDITY";
    public static final String INTERVAL = "INTERVAL";
    public static final String BAROMABSIN = "BAROMABSIN";
    public static final String BAROMRELIN = "BAROMRELIN";
    public static final String HUMIDITYIN = "HUMIDITYIN";
    public static final String RAINRATEIN = "RAINRATEIN";
    public static final String WH_25_BATT = "WH_25_BATT";
    public static final String WH_65_BATT = "WH_65_BATT";
    public static final String DAILYRAININ = "DAILYRAININ";
    public static final String EVENTRAININ = "EVENTRAININ";
    public static final String STATIONTYPE = "STATIONTYPE";
    public static final String WINDGUSTMPH = "WINDGUSTMPH";
    public static final String HOURLYRAININ = "HOURLYRAININ";
    public static final String MAXDAILYGUST = "MAXDAILYGUST";
    public static final String WEEKLYRAININ = "WEEKLYRAININ";
    public static final String WINDSPEEDMPH = "WINDSPEEDMPH";
    public static final String YEARLYRAININ = "YEARLYRAININ";
    public static final String MONTHLYRAININ = "MONTHLYRAININ";
    public static final String SOLARRADIATION = "SOLARRADIATION";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_station_generator")
    @SequenceGenerator(name = "weather_station_generator", sequenceName = "WEATHER_STATION_SEQ", allocationSize = 1)
    @Column(name = ID)
    private Integer id;

    @Column(name = UV)
    private Integer uv;

    @Column(name = FREQ)
    private String freq;

    @Column(name = HEAP)
    private Integer heap;

    @Column(name = MODEL)
    private String model;

    @Column(name = TEMPF)
    private Float tempf;

    @Column(name = DATEUTC)
    private OffsetDateTime dateutc;

    @Column(name = PASSKEY)
    private String passkey;

    @Column(name = RUNTIME)
    private Integer runtime;

    @Column(name = TEMPINF)
    private Float tempinf;

    @Column(name = WINDDIR)
    private Integer winddir;

    @Column(name = HUMIDITY)
    private Integer humidity;

    @Column(name = INTERVAL)
    private Integer interval;

    @Column(name = BAROMABSIN)
    private Float baromabsin;

    @Column(name = BAROMRELIN)
    private Float baromrelin;

    @Column(name = HUMIDITYIN)
    private Integer humidityin;

    @Column(name = RAINRATEIN)
    private Float rainratein;

    @Column(name = WH_25_BATT)
    private Integer wh25batt;

    @Column(name = WH_65_BATT)
    private Integer wh65batt;

    @Column(name = DAILYRAININ)
    private Float dailyrainin;

    @Column(name = EVENTRAININ)
    private Float eventrainin;

    @Column(name = STATIONTYPE)
    private String stationtype;

    @Column(name = WINDGUSTMPH)
    private Float windgustmph;

    @Column(name = HOURLYRAININ)
    private Float hourlyrainin;

    @Column(name = MAXDAILYGUST)
    private Float maxdailygust;

    @Column(name = WEEKLYRAININ)
    private Float weeklyrainin;

    @Column(name = WINDSPEEDMPH)
    private Float windspeedmph;

    @Column(name = YEARLYRAININ)
    private Float yearlyrainin;

    @Column(name = MONTHLYRAININ)
    private Float monthlyrainin;

    @Column(name = SOLARRADIATION)
    private Float solarradiation;

    /**
     * Needed for Hibernate
     */
    public WeatherStationEntryEntity() {
    }

    public Integer getId() {
        return id;
    }

    public OffsetDateTime getDateutc() {
        return dateutc;
    }

    public String getModel() {
        return model;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public Float getTempf() {
        return tempf;
    }

    public String getPasskey() {
        return passkey;
    }

    public Integer getUv() {
        return uv;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Float getTempinf() {
        return tempinf;
    }

    public Float getBaromabsin() {
        return baromabsin;
    }

    public Integer getWinddir() {
        return winddir;
    }

    public Integer getInterval() {
        return interval;
    }

    public String getFreq() {
        return freq;
    }

    public Integer getHeap() {
        return heap;
    }

    public Float getBaromrelin() {
        return baromrelin;
    }

    public Integer getHumidityin() {
        return humidityin;
    }

    public Float getRainratein() {
        return rainratein;
    }

    public Integer getWh25batt() {
        return wh25batt;
    }

    public Integer getWh65batt() {
        return wh65batt;
    }

    public Float getDailyrainin() {
        return dailyrainin;
    }

    public Float getEventrainin() {
        return eventrainin;
    }

    public String getStationtype() {
        return stationtype;
    }

    public Float getWindgustmph() {
        return windgustmph;
    }

    public Float getHourlyrainin() {
        return hourlyrainin;
    }

    public Float getMaxdailygust() {
        return maxdailygust;
    }

    public Float getWeeklyrainin() {
        return weeklyrainin;
    }

    public Float getWindspeedmph() {
        return windspeedmph;
    }

    public Float getYearlyrainin() {
        return yearlyrainin;
    }

    public Float getMonthlyrainin() {
        return monthlyrainin;
    }

    public Float getSolarradiation() {
        return solarradiation;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public void setHeap(Integer heap) {
        this.heap = heap;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTempf(Float tempf) {
        this.tempf = tempf;
    }

    public void setDateutc(OffsetDateTime dateutc) {
        this.dateutc = dateutc;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public void setTempinf(Float tempinf) {
        this.tempinf = tempinf;
    }

    public void setWinddir(Integer winddir) {
        this.winddir = winddir;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public void setBaromabsin(Float baromabsin) {
        this.baromabsin = baromabsin;
    }

    public void setBaromrelin(Float baromrelin) {
        this.baromrelin = baromrelin;
    }

    public void setHumidityin(Integer humidityin) {
        this.humidityin = humidityin;
    }

    public void setRainratein(Float rainratein) {
        this.rainratein = rainratein;
    }

    public void setWh25batt(Integer wh25batt) {
        this.wh25batt = wh25batt;
    }

    public void setWh65batt(Integer wh65batt) {
        this.wh65batt = wh65batt;
    }

    public void setDailyrainin(Float dailyrainin) {
        this.dailyrainin = dailyrainin;
    }

    public void setEventrainin(Float eventrainin) {
        this.eventrainin = eventrainin;
    }

    public void setStationtype(String stationtype) {
        this.stationtype = stationtype;
    }

    public void setWindgustmph(Float windgustmph) {
        this.windgustmph = windgustmph;
    }

    public void setHourlyrainin(Float hourlyrainin) {
        this.hourlyrainin = hourlyrainin;
    }

    public void setMaxdailygust(Float maxdailygust) {
        this.maxdailygust = maxdailygust;
    }

    public void setWeeklyrainin(Float weeklyrainin) {
        this.weeklyrainin = weeklyrainin;
    }

    public void setWindspeedmph(Float windspeedmph) {
        this.windspeedmph = windspeedmph;
    }

    public void setYearlyrainin(Float yearlyrainin) {
        this.yearlyrainin = yearlyrainin;
    }

    public void setMonthlyrainin(Float monthlyrainin) {
        this.monthlyrainin = monthlyrainin;
    }

    public void setSolarradiation(Float solarradiation) {
        this.solarradiation = solarradiation;
    }
}