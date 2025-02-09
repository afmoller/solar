CREATE TABLE WEATHER_STATION (
    ID INTEGER NOT NULL,
    UV INTEGER NOT NULL,
    FREQ TEXT NOT NULL,
    HEAP INTEGER NOT NULL,
    MODEL TEXT NOT NULL,
    TEMPF NUMERIC(5, 1) NOT NULL,
    DATEUTC TIMESTAMPTZ NOT NULL,
    PASSKEY TEXT NOT NULL,
    RUNTIME INTEGER NOT NULL,
    TEMPINF NUMERIC(5, 1) NOT NULL,
    WINDDIR INTEGER NOT NULL,
    HUMIDITY INTEGER NOT NULL,
    INTERVAL INTEGER NOT NULL,
    BAROMABSIN NUMERIC(5, 3) NOT NULL,
    BAROMRELIN NUMERIC(5, 3) NOT NULL,
    HUMIDITYIN INTEGER NOT NULL,
    RAINRATEIN NUMERIC(6, 3) NOT NULL,
    WH_25_BATT INTEGER NOT NULL,
    WH_65_BATT INTEGER NOT NULL,
    DAILYRAININ NUMERIC(6, 3) NOT NULL,
    EVENTRAININ NUMERIC(6, 3) NOT NULL,
    STATIONTYPE TEXT NOT NULL,
    WINDGUSTMPH NUMERIC(5, 2) NOT NULL,
    HOURLYRAININ NUMERIC(6, 3) NOT NULL,
    MAXDAILYGUST NUMERIC(5, 2) NOT NULL,
    WEEKLYRAININ NUMERIC(6, 3) NOT NULL,
    WINDSPEEDMPH NUMERIC(5, 2) NOT NULL,
    YEARLYRAININ NUMERIC(6, 3) NOT NULL,
    MONTHLYRAININ NUMERIC(6, 3) NOT NULL,
    SOLARRADIATION NUMERIC(6, 2) NOT NULL
);