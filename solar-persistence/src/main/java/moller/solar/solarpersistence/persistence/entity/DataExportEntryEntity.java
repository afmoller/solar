package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "DATA_EXPORT")
public class DataExportEntryEntity {

    public static final String IID = "IID";
    public static final String ID = "ID";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String TIMESTAMP_YEAR = "TIMESTAMP_YEAR";
    public static final String TIMESTAMP_MONTH = "TIMESTAMP_MONTH";
    public static final String TIMESTAMP_DAY = "TIMESTAMP_DAY";
    public static final String DI_1 = "DI_1";
    public static final String DI_2 = "DI_2";
    public static final String DI_3 = "DI_3";
    public static final String DI_4 = "DI_4";
    public static final String DI_5 = "DI_5";
    public static final String DI_6 = "DI_6";
    public static final String DI_7 = "DI_7";
    public static final String DI_8 = "DI_8";
    public static final String IMPORT_READ = "IMPORT_READ";
    public static final String DI_9 = "DI_9";
    public static final String DI_10 = "DI_10";
    public static final String UPDATE_TIME = "UPDATE_TIME";
    public static final String IP = "IP";
    public static final String WLAN = "WLAN";
    public static final String VERSION = "VERSION";
    public static final String DI_11 = "DI_11";
    public static final String DI_12 = "DI_12";
    public static final String DI_13 = "DI_13";
    public static final String DI_14 = "DI_14";
    public static final String DI_15 = "DI_15";
    public static final String DI_16 = "DI_16";
    public static final String DI_17 = "DI_17";
    public static final String DI_18 = "DI_18";
    public static final String DI_19 = "DI_19";
    public static final String DI_20 = "DI_20";
    public static final String MULTICHANNEL_SWITCH_OVERRIDE = "MULTICHANNEL_SWITCH_OVERRIDE";

    @Id
    @Column(name = IID)
    private Integer iid;

    @Column(name = ID)
    private Integer id;

    @Column(name = TIMESTAMP)
    private LocalDateTime timestamp;

    @Column(name = TIMESTAMP_YEAR)
    private int timestampYear;

    @Column(name = TIMESTAMP_MONTH)
    private int timestampMonth;

    @Column(name = TIMESTAMP_DAY)
    private int timestampDay;

    @Column(name = DI_1)
    private Integer di1;

    @Column(name = DI_2)
    private Integer di2;

    @Column(name = DI_3)
    private Integer di3;

    @Column(name = DI_4)
    private Integer di4;

    @Column(name = DI_5)
    private Integer di5;

    @Column(name = DI_6)
    private Integer di6;

    @Column(name = DI_7)
    private Integer di7;

    @Column(name = DI_8)
    private Integer di8;

    @Column(name = IMPORT_READ)
    private Integer importRead;

    @Column(name = DI_9)
    private Integer di9;

    @Column(name = DI_10)
    private Integer di10;

    @Column(name = UPDATE_TIME)
    private LocalDateTime updateTime;

    @Column(name = IP)
    private String ip;

    @Column(name = WLAN)
    private String wlan;

    @Column(name = VERSION)
    private String version;

    @Column(name = DI_11)
    private Integer di11;

    @Column(name = DI_12)
    private Integer di12;

    @Column(name = DI_13)
    private Integer di13;

    @Column(name = DI_14)
    private Integer di14;

    @Column(name = DI_15)
    private Integer di15;

    @Column(name = DI_16)
    private Integer di16;

    @Column(name = DI_17)
    private Integer di17;

    @Column(name = DI_18)
    private Integer di18;

    @Column(name = DI_19)
    private Integer di19;

    @Column(name = DI_20)
    private Integer di20;

    @Column(name = MULTICHANNEL_SWITCH_OVERRIDE)
    private String multichannelSwitchOverride;

    /**
     * Needed for Hibernate
     */
    public DataExportEntryEntity() {
    }

    public Integer getIid() {
        return iid;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getTimestampYear() {
        return timestampYear;
    }

    public int getTimestampMonth() {
        return timestampMonth;
    }

    public int getTimestampDay() {
        return timestampDay;
    }

    public Integer getDi1() {
        return di1;
    }

    public Integer getDi2() {
        return di2;
    }

    public Integer getDi3() {
        return di3;
    }

    public Integer getDi4() {
        return di4;
    }

    public Integer getDi5() {
        return di5;
    }

    public Integer getDi6() {
        return di6;
    }

    public Integer getDi7() {
        return di7;
    }

    public Integer getDi8() {
        return di8;
    }

    public Integer getImportRead() {
        return importRead;
    }

    public Integer getDi9() {
        return di9;
    }

    public Integer getDi10() {
        return di10;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public String getIp() {
        return ip;
    }

    public String getWlan() {
        return wlan;
    }

    public String getVersion() {
        return version;
    }

    public Integer getDi11() {
        return di11;
    }

    public Integer getDi12() {
        return di12;
    }

    public Integer getDi13() {
        return di13;
    }

    public Integer getDi14() {
        return di14;
    }

    public Integer getDi15() {
        return di15;
    }

    public Integer getDi16() {
        return di16;
    }

    public Integer getDi17() {
        return di17;
    }

    public Integer getDi18() {
        return di18;
    }

    public Integer getDi19() {
        return di19;
    }

    public Integer getDi20() {
        return di20;
    }

    public String getMultichannelSwitchOverride() {
        return multichannelSwitchOverride;
    }
}