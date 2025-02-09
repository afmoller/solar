package moller.solar.solarpersistence.controller;

import moller.openapi.persistence.solar.api.DataExportControllerApi;
import moller.openapi.persistence.solar.model.DataExportEntry;
import moller.solar.solarpersistence.service.DataExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
public class DataExportController implements DataExportControllerApi {

    private final DataExportService dataExportService;

    public DataExportController(
            DataExportService dataExportService) {

        this.dataExportService = dataExportService;
    }

    @Override
    public ResponseEntity<DataExportEntry> getDataExportEntryByIID(Integer iid) {
        Optional<DataExportEntry> dataExportEntryByIID = dataExportService.getDataExportEntryByIID(iid);
        return ResponseEntity.of(dataExportEntryByIID);
    }

    @Override
    public ResponseEntity<List<DataExportEntry>> getDateTimeAndValuesForTimespan(LocalDate selectedFromDate, LocalDate selectedToDate) {
        LocalDateTime localDateTimeFrom = LocalDateTime.of(selectedFromDate, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeTo = LocalDateTime.of(selectedToDate.plusDays(1), LocalTime.MIDNIGHT).minusSeconds(1);

        List<DataExportEntry> byTimespanOrderedByTimestamp = this.dataExportService.findByTimespanOrderedByTimestamp(localDateTimeFrom, localDateTimeTo);

        return ResponseEntity.of(Optional.of(byTimespanOrderedByTimestamp));
    }

    @Override
    public ResponseEntity<List<DataExportEntry>> findByYearAndMonth(Integer year, Integer month) {
        List<DataExportEntry> byYearAndMonth = dataExportService.findByYearAndMonth(year, month);

        return ResponseEntity.of(Optional.ofNullable(byYearAndMonth));
    }
}
