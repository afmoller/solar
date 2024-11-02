package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.datetimeandvalues.DateTimeAndValues;
import moller.solar.solarbackend.dto.datetimeandvalues.DateTimeAndValuesWattages;
import moller.solar.solarbackend.dto.datetimeandvalues.DateTimeAndValuesWatthours;
import moller.solar.solarbackend.enumerations.Resolution;
import moller.solarpersistence.openapi.client.api.DataExportControllerApi;
import moller.solarpersistence.openapi.client.model.DataExportEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DataExportController extends AbstractV1BaseController {

    private final DataExportControllerApi dataExportControllerApi;

    public DataExportController(DataExportControllerApi dataExportControllerApi) {
        this.dataExportControllerApi = dataExportControllerApi;
    }

    @GetMapping(value = "/getDataExportEntryByIID")
    public ResponseEntity<DataExportEntry> getDataExportEntryByIID(@RequestParam("iid") Integer iid) {
        return ResponseEntity.of(Optional.of(dataExportControllerApi.getDataExportEntryByIID(iid)));
    }

    @GetMapping(value = "/getDateTimeAndValuesForTimespan")
    public ResponseEntity<DateTimeAndValues> getDateTimeAndValuesForTimespan(@RequestParam Resolution resolution, @RequestParam LocalDate selectedFromDate, @RequestParam LocalDate selectedToDate) {
        List<DataExportEntry> dataExportEntries = dataExportControllerApi.getDateTimeAndValuesForTimespan(selectedFromDate, selectedToDate);

        switch (resolution) {
            case MINUTE -> {
                return ResponseEntity.of(Optional.of(DateTimeAndValuesWattages.createDateTimeAndValues(dataExportEntries)));
            }
            case HOUR -> {
                List<DataExportEntry> dataExportEntriesAggregatedOnHour = aggregateOnHour(dataExportEntries);
                return ResponseEntity.of(Optional.of(DateTimeAndValuesWatthours.createDateTimeAndValues(dataExportEntriesAggregatedOnHour)));
            }
            case DAY, MONTH, YEAR -> {
                return ResponseEntity.of(Optional.empty());
            }
            default -> throw new IllegalStateException("Unexpected value: " + resolution);
        }
    }

    private List<DataExportEntry> aggregateOnHour(List<DataExportEntry> dataExportEntries) {
        List<DataExportEntry> dataExportEntriesAggregatedOnHour = new ArrayList<>();

        int hour = -1;

        LocalDateTime dateTime = null;
        int saleImpulses = 0;
        int purchaseImpulses = 0;
        int productionImpulses = 0;
        int consumptionImpulses = 0;
        int selfConsumptionImpulses = 0;

        int numberOfEntries = dataExportEntries.size();

        for (int index = 0; index < numberOfEntries; index++) {

            DataExportEntry dataExportEntry = dataExportEntries.get(index);

            int currentHour = dataExportEntry.getTimestamp().getHour();

            if (hour != currentHour || index == numberOfEntries - 1) {
                if (hour != -1) {

                    DataExportEntry currentAggregatedDataExportEntry = new DataExportEntry.Builder()
                            .timestamp(dateTime)
                            .di3(saleImpulses)
                            .di4(purchaseImpulses)
                            .di1(productionImpulses)
                            .di2(consumptionImpulses)
                            .di5(selfConsumptionImpulses)
                            .build();

                    dataExportEntriesAggregatedOnHour.add(currentAggregatedDataExportEntry);

                    saleImpulses = 0;
                    purchaseImpulses = 0;
                    productionImpulses = 0;
                    consumptionImpulses = 0;
                    selfConsumptionImpulses = 0;
                }
            }

            dateTime =  dataExportEntry.getTimestamp().withMinute(0).withSecond(0);
            saleImpulses += dataExportEntry.getDi3();
            purchaseImpulses += dataExportEntry.getDi4();
            productionImpulses += dataExportEntry.getDi1();
            consumptionImpulses += dataExportEntry.getDi2();
            selfConsumptionImpulses += dataExportEntry.getDi5();

            hour = currentHour;
        }

        return dataExportEntriesAggregatedOnHour;
    }
}
