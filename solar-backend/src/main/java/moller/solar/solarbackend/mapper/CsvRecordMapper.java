package moller.solar.solarbackend.mapper;

import moller.solarpersistence.openapi.client.model.DataExportEntry;
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

        return new DataExportEntry.Builder()
                .di1(Integer.valueOf(get(csvRecord,DI_1)))
                .di2(Integer.valueOf(get(csvRecord,DI_2)))
                .di3(Integer.valueOf(get(csvRecord,DI_3)))
                .di4(Integer.valueOf(get(csvRecord,DI_4)))
                .di5(Integer.valueOf(get(csvRecord,DI_5)))
                .di6(Integer.valueOf(get(csvRecord,DI_6)))
                .di7(Integer.valueOf(get(csvRecord,DI_7)))
                .di8(Integer.valueOf(get(csvRecord,DI_8)))
                .di9(Integer.valueOf(get(csvRecord,DI_9)))
                .di10(Integer.valueOf(get(csvRecord,DI_10)))
                .di11(Double.valueOf(get(csvRecord,DI_11)))
                .di12(Double.valueOf(get(csvRecord,DI_12)))
                .di13(Double.valueOf(get(csvRecord,DI_13)))
                .di14(Integer.valueOf(get(csvRecord,DI_14)))
                .di15(Integer.valueOf(get(csvRecord,DI_15)))
                .di16(Integer.valueOf(get(csvRecord,DI_16)))
                .di17(Integer.valueOf(get(csvRecord,DI_17)))
                .di18(Integer.valueOf(get(csvRecord,DI_18)))
                .di19(Integer.valueOf(get(csvRecord,DI_19)))
                .di20(Integer.valueOf(get(csvRecord,DI_20)))
                .id(Integer.valueOf(get(csvRecord,ID)))
                .iid(Integer.valueOf(get(csvRecord,IID)))
                .ip(get(csvRecord,IP))
                .timestamp(timeStamp)
                .timestampYear(timeStamp.getYear())
                .timestampMonth(timeStamp.getMonthValue())
                .timestampDay(timeStamp.getDayOfMonth())
                .importRead(Integer.valueOf(get(csvRecord,IMPORT_READ)))
                .version(get(csvRecord,VERSION))
                .wlan(get(csvRecord,WLAN))
                .updateTime(LocalDateTime.parse(get(csvRecord,UPDATE_TIME), LOCAL_DATE_TIME))
                .multichannelSwitchOverride(get(csvRecord,MULTICHANNEL_SWITCH_OVERRIDE))
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
