package moller.solar.solarbackend.mapper;

public enum CsvHeaderColumnName {
    IID("iid", "IID"),
    ID("id","ID"),
    TIMESTAMP("timestamp","TIMESTAMP"),
    DI_1("di1","DI_1"),
    DI_2("di2","DI_2"),
    DI_3("di3","DI_3"),
    DI_4("di4","DI_4"),
    DI_5("di5","DI_5"),
    DI_6("di6","DI_6"),
    DI_7("di7","DI_7"),
    DI_8("di8","DI_8"),
    IMPORT_READ("import_read","IMPORT_READ"),
    DI_9("di9","DI_9"),
    DI_10("di10","DI_10"),
    UPDATE_TIME("update_time","UPDATE_TIME"),
    IP("ip","IP"),
    WLAN("wlan","WLAN"),
    VERSION("version","VERSION"),
    DI_11("di11","DI_11"),
    DI_12("di12","DI_12"),
    DI_13("di13","DI_13"),
    DI_14("di14","DI_14"),
    DI_15("di15","DI_15"),
    DI_16("di16","DI_16"),
    DI_17("di17","DI_17"),
    DI_18("di18","DI_18"),
    DI_19("di19","DI_19"),
    DI_20("di20","DI_20"),
    MULTICHANNEL_SWITCH_OVERRIDE("multichannel_switch_override","MULTICHANNEL_SWITCH_OVERRIDE");

    private final String csvHeaderName;
    private final String dbTableColumnName;

    CsvHeaderColumnName(String csvHeaderName, String dbTableColumnName) {
        this.csvHeaderName = csvHeaderName;
        this.dbTableColumnName = dbTableColumnName;
    }

    public String getCsvHeaderName() {
        return csvHeaderName;
    }

    public String getDbTableColumnName() {
        return dbTableColumnName;
    }
}
