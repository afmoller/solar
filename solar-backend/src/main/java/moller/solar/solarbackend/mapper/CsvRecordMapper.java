package moller.solar.solarbackend.mapper;

import moller.solar.solarbackend.persistence.DataExportEntry;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static moller.solar.solarbackend.mapper.CsvHeaderColumnName.*;

@Component
public class CsvRecordMapper {

    public DataExportEntry mapToDataExportEntry(CSVRecord csvRecord) {
        LocalDateTime timeStamp = LocalDateTime.parse(get(csvRecord, TIMESTAMP), LOCAL_DATE_TIME);

        return new DataExportEntry.DateExportEntryBuilder()
                .setDi_1(Integer.valueOf(get(csvRecord,DI_1)))
                .setDi_2(Integer.valueOf(get(csvRecord,DI_2)))
                .setDi_3(Integer.valueOf(get(csvRecord,DI_3)))
                .setDi_4(Integer.valueOf(get(csvRecord,DI_4)))
                .setDi_5(Integer.valueOf(get(csvRecord,DI_5)))
                .setDi_6(Integer.valueOf(get(csvRecord,DI_6)))
                .setDi_7(Integer.valueOf(get(csvRecord,DI_7)))
                .setDi_8(Integer.valueOf(get(csvRecord,DI_8)))
                .setDi_9(Integer.valueOf(get(csvRecord,DI_9)))
                .setDi_10(Integer.valueOf(get(csvRecord,DI_10)))
                .setDi_11(Integer.valueOf(get(csvRecord,DI_11)))
                .setDi_12(Integer.valueOf(get(csvRecord,DI_12)))
                .setDi_13(Integer.valueOf(get(csvRecord,DI_13)))
                .setDi_14(Integer.valueOf(get(csvRecord,DI_14)))
                .setDi_15(Integer.valueOf(get(csvRecord,DI_15)))
                .setDi_16(Integer.valueOf(get(csvRecord,DI_16)))
                .setDi_17(Integer.valueOf(get(csvRecord,DI_17)))
                .setDi_18(Integer.valueOf(get(csvRecord,DI_18)))
                .setDi_19(Integer.valueOf(get(csvRecord,DI_19)))
                .setDi_20(Integer.valueOf(get(csvRecord,DI_20)))
                .setId(Integer.valueOf(get(csvRecord,ID)))
                .setIid(Integer.valueOf(get(csvRecord,IID)))
                .setIp(get(csvRecord,IP))
                .setTimestamp(timeStamp)
                .setTimestampYear(timeStamp.getYear())
                .setTimestampMonth(timeStamp.getMonthValue())
                .setTimestampDay(timeStamp.getDayOfMonth())
                .setImportRead(Integer.valueOf(get(csvRecord,IMPORT_READ)))
                .setVersion(get(csvRecord,VERSION))
                .setWlan(get(csvRecord,WLAN))
                .setUpdateTime(LocalDateTime.parse(get(csvRecord,UPDATE_TIME), LOCAL_DATE_TIME))
                .setMultichannelSwitchOverride(get(csvRecord,MULTICHANNEL_SWITCH_OVERRIDE))
                .build();
    }

    private String get(CSVRecord csvRecord, CsvHeaderColumnName csvHeaderColumnName) {
        return csvRecord.get(csvHeaderColumnName.getCsvHeaderName());
    }

    public static final DateTimeFormatter LOCAL_DATE_TIME;
    static {
        LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME)
                .toFormatter();
    }
}
