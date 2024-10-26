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

    private DataExportEntryEntity(DateExportEntryBuilder dateExportEntryBuilder) {
        this.iid = dateExportEntryBuilder.iid;
        this.id = dateExportEntryBuilder.id;
        this.timestamp = dateExportEntryBuilder.timestamp;
        this.timestampYear = dateExportEntryBuilder.timestampYear;
        this.timestampMonth = dateExportEntryBuilder.timestampMonth;
        this.timestampDay = dateExportEntryBuilder.timestampDay;
        this.di1 = dateExportEntryBuilder.di1;
        this.di2 = dateExportEntryBuilder.di2;
        this.di3 = dateExportEntryBuilder.di3;
        this.di4 = dateExportEntryBuilder.di4;
        this.di5 = dateExportEntryBuilder.di5;
        this.di6 = dateExportEntryBuilder.di6;
        this.di7 = dateExportEntryBuilder.di7;
        this.di8 = dateExportEntryBuilder.di8;
        this.importRead = dateExportEntryBuilder.importRead;
        this.di9 = dateExportEntryBuilder.di9;
        this.di10 = dateExportEntryBuilder.di10;
        this.updateTime = dateExportEntryBuilder.updateTime;
        this.ip = dateExportEntryBuilder.ip;
        this.wlan = dateExportEntryBuilder.wlan;
        this.version = dateExportEntryBuilder.version;
        this.di11 = dateExportEntryBuilder.di11;
        this.di12 = dateExportEntryBuilder.di12;
        this.di13 = dateExportEntryBuilder.di13;
        this.di14 = dateExportEntryBuilder.di14;
        this.di15 = dateExportEntryBuilder.di15;
        this.di16 = dateExportEntryBuilder.di16;
        this.di17 = dateExportEntryBuilder.di17;
        this.di18 = dateExportEntryBuilder.di18;
        this.di19 = dateExportEntryBuilder.di19;
        this.di20 = dateExportEntryBuilder.di20;
        this.multichannelSwitchOverride = dateExportEntryBuilder.multichannelSwitchOverride;
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

    public static class DateExportEntryBuilder {
        private Integer iid;
        private Integer id;
        private LocalDateTime timestamp;
        public int timestampYear;
        public int timestampMonth;
        public int timestampDay;
        private Integer di1;
        private Integer di2;
        private Integer di3;
        private Integer di4;
        private Integer di5;
        private Integer di6;
        private Integer di7;
        private Integer di8;
        private Integer importRead;
        private Integer di9;
        private Integer di10;
        private LocalDateTime updateTime;
        private String ip;
        private String wlan;
        private String version;
        private Integer di11;
        private Integer di12;
        private Integer di13;
        private Integer di14;
        private Integer di15;
        private Integer di16;
        private Integer di17;
        private Integer di18;
        private Integer di19;
        private Integer di20;
        private String multichannelSwitchOverride;

        public DateExportEntryBuilder setIid(Integer iid) {
            this.iid = iid;
            return this;
        }

        public DateExportEntryBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public DateExportEntryBuilder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DateExportEntryBuilder setTimestampYear(int timestampYear) {
            this.timestampYear = timestampYear;
            return this;
        }

        public DateExportEntryBuilder setTimestampMonth(int timestampMonth) {
            this.timestampMonth = timestampMonth;
            return this;
        }

        public DateExportEntryBuilder setTimestampDay(int timestampDay) {
            this.timestampDay = timestampDay;
            return this;
        }

        public DateExportEntryBuilder setDi_1(Integer di1) {
            this.di1 = di1;
            return this;
        }

        public DateExportEntryBuilder setDi_2(Integer di2) {
            this.di2 = di2;
            return this;
        }

        public DateExportEntryBuilder setDi_3(Integer di3) {
            this.di3 = di3;
            return this;
        }

        public DateExportEntryBuilder setDi_4(Integer di4) {
            this.di4 = di4;
            return this;
        }

        public DateExportEntryBuilder setDi_5(Integer di5) {
            this.di5 = di5;
            return this;
        }

        public DateExportEntryBuilder setDi_6(Integer di6) {
            this.di6 = di6;
            return this;
        }

        public DateExportEntryBuilder setDi_7(Integer di7) {
            this.di7 = di7;
            return this;
        }

        public DateExportEntryBuilder setDi_8(Integer di8) {
            this.di8 = di8;
            return this;
        }

        public DateExportEntryBuilder setImportRead(Integer importRead) {
            this.importRead = importRead;
            return this;
        }

        public DateExportEntryBuilder setDi_9(Integer di9) {
            this.di9 = di9;
            return this;
        }

        public DateExportEntryBuilder setDi_10(Integer di10) {
            this.di10 = di10;
            return this;
        }

        public DateExportEntryBuilder setUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public DateExportEntryBuilder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public DateExportEntryBuilder setWlan(String wlan) {
            this.wlan = wlan;
            return this;
        }

        public DateExportEntryBuilder setVersion(String version) {
            this.version = version;
            return this;
        }

        public DateExportEntryBuilder setDi_11(Integer di11) {
            this.di11 = di11;
            return this;
        }

        public DateExportEntryBuilder setDi_12(Integer di12) {
            this.di12 = di12;
            return this;
        }

        public DateExportEntryBuilder setDi_13(Integer di13) {
            this.di13 = di13;
            return this;
        }

        public DateExportEntryBuilder setDi_14(Integer di14) {
            this.di14 = di14;
            return this;
        }

        public DateExportEntryBuilder setDi_15(Integer di15) {
            this.di15 = di15;
            return this;
        }

        public DateExportEntryBuilder setDi_16(Integer di16) {
            this.di16 = di16;
            return this;
        }

        public DateExportEntryBuilder setDi_17(Integer di17) {
            this.di17 = di17;
            return this;
        }

        public DateExportEntryBuilder setDi_18(Integer di18) {
            this.di18 = di18;
            return this;
        }

        public DateExportEntryBuilder setDi_19(Integer di19) {
            this.di19 = di19;
            return this;
        }

        public DateExportEntryBuilder setDi_20(Integer di20) {
            this.di20 = di20;
            return this;
        }

        public DateExportEntryBuilder setMultichannelSwitchOverride(String multichannelSwitchOverride) {
            this.multichannelSwitchOverride = multichannelSwitchOverride;
            return this;
        }

        public DataExportEntryEntity build() {
            return new DataExportEntryEntity(this);
        }
    }
}
