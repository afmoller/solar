CREATE TABLE ENERGY_COST (
    ID INTEGER PRIMARY KEY NOT NULL,
    FROM_DATE DATE NOT NULL,
    TO_DATE DATE NOT NULL,
    ENERGY_COST_PER_KWH_IN_TEN_THOUSANDS INTEGER NOT NULL,
    ELECTRICAL_GRID_COST_IN_TEN_THOUSANDS INTEGER NOT NULL,
    FEE_ONE_IN_TEN_THOUSANDS INTEGER NOT NULL,
    FEE_TWO_IN_TEN_THOUSANDS INTEGER NOT NULL,
    VALUE_ADDED_TAX_PERCENTAGE_RATE_IN_MINOR_UNIT INTEGER NOT NULL
);