package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.DateTimeAndValues;
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
    public ResponseEntity<DateTimeAndValues> getDateTimeAndValuesForTimespan(@RequestParam LocalDate selectedFromDate, @RequestParam LocalDate selectedToDate) {
        LocalDateTime localDateTimeFrom = LocalDateTime.of(selectedFromDate, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeTo = LocalDateTime.of(selectedToDate.plusDays(1), LocalTime.MIDNIGHT).minusSeconds(1);
        List<DataExportEntry> dataExportEntries = dataExportRepository.findByTimespan(localDateTimeFrom, localDateTimeTo);

        return ResponseEntity.of(Optional.of(DateTimeAndValues.createDateTimeAndValues(dataExportEntries)));
    }
}
