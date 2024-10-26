package moller.solar.solarpersistence.delegate;

import moller.solar.solarpersistence.service.DataExportService;
import moller.solarpersistence.openapi.api.DataExportControllerApiDelegate;
import moller.solarpersistence.openapi.model.DataExportEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class DataExportControllerApiDelegateImpl implements DataExportControllerApiDelegate {

    private final DataExportService dataExportService;

    public DataExportControllerApiDelegateImpl(
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
}
