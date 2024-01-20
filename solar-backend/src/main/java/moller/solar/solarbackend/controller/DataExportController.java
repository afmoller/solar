package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.datetimeandvalues.DateTimeAndValues;
import moller.solar.solarbackend.dto.datetimeandvalues.DateTimeAndValuesWattages;
import moller.solar.solarbackend.dto.datetimeandvalues.DateTimeAndValuesWatthours;
import moller.solar.solarbackend.enumerations.Resolution;
import moller.solar.solarbackend.persistence.DataExportEntry;
import moller.solar.solarbackend.persistence.DataExportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class DataExportController {

    private final DataExportRepository dataExportRepository;

    public DataExportController(DataExportRepository dataExportRepository) {
        this.dataExportRepository = dataExportRepository;
    }

    @GetMapping(value = "/getDataExportEntryByIID")
    public ResponseEntity<DataExportEntry> getDataExportEntryByIID(@RequestParam("iid") Integer iid) {
        return ResponseEntity.of(dataExportRepository.findById(iid));
    }

    @GetMapping(value = "/getDateTimeAndValuesForTimespan")
    public ResponseEntity<DateTimeAndValues> getDateTimeAndValuesForTimespan(@RequestParam Resolution resolution, @RequestParam LocalDate selectedFromDate, @RequestParam LocalDate selectedToDate) {
        LocalDateTime localDateTimeFrom = LocalDateTime.of(selectedFromDate, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeTo = LocalDateTime.of(selectedToDate.plusDays(1), LocalTime.MIDNIGHT).minusSeconds(1);
        List<DataExportEntry> dataExportEntries = dataExportRepository.findByTimespanOrderedByTimestamp(localDateTimeFrom, localDateTimeTo);

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

                    DataExportEntry currentAggregatedDataExportEntry = new DataExportEntry.DateExportEntryBuilder()
                            .setTimestamp(dateTime)
                            .setDi_3(saleImpulses)
                            .setDi_4(purchaseImpulses)
                            .setDi_1(productionImpulses)
                            .setDi_2(consumptionImpulses)
                            .setDi_5(selfConsumptionImpulses)
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
            saleImpulses += dataExportEntry.getDi_3();
            purchaseImpulses += dataExportEntry.getDi_4();
            productionImpulses += dataExportEntry.getDi_1();
            consumptionImpulses += dataExportEntry.getDi_2();
            selfConsumptionImpulses += dataExportEntry.getDi_5();

            hour = currentHour;
        }

        return dataExportEntriesAggregatedOnHour;
    }
}
