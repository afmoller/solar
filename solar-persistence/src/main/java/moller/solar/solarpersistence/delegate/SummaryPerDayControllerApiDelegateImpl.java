package moller.solar.solarpersistence.delegate;

import jakarta.validation.Valid;
import moller.solar.solarpersistence.service.SummaryPerDayService;
import moller.solarpersistence.openapi.api.SummaryPerDayControllerApiDelegate;
import moller.solarpersistence.openapi.model.SummaryPerDayEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SummaryPerDayControllerApiDelegateImpl implements SummaryPerDayControllerApiDelegate {

    private final SummaryPerDayService summaryPerDayService;

    public SummaryPerDayControllerApiDelegateImpl(
            SummaryPerDayService summaryPerDayService) {

        this.summaryPerDayService = summaryPerDayService;
    }

    @Override
    public ResponseEntity<List<SummaryPerDayEntry>> getAllValues() {
        List<SummaryPerDayEntry> allValues = summaryPerDayService.getAllValues();

        if (allValues.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allValues));
    }

    @Override
    public ResponseEntity<List<SummaryPerDayEntry>> getAllValuesForPeriod(LocalDate fromDate, LocalDate toDate) {
        List<SummaryPerDayEntry> allValuesForPeriod = summaryPerDayService.getAllValuesForPeriod(fromDate, toDate);

        if (allValuesForPeriod.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allValuesForPeriod));
    }

    @Override
    public ResponseEntity<SummaryPerDayEntry> getEntryWithHighestAccumulatedValues() {
        SummaryPerDayEntry entryWithHighestAccumulatedValues = summaryPerDayService.getEntryWithHighestAccumulatedValues();

        return ResponseEntity.of(Optional.of(entryWithHighestAccumulatedValues));
    }

    @Override
    public ResponseEntity<SummaryPerDayEntry> getNewestEntry() {
        SummaryPerDayEntry newestEntry = summaryPerDayService.getNewestEntry();

        return ResponseEntity.of(Optional.of(newestEntry));
    }

    @Override
    public ResponseEntity<Integer> saveSummaryPerDayEntries(List<@Valid SummaryPerDayEntry> summaryPerDayEntries) {
        Integer numberOfSavedEntries = summaryPerDayService.saveSummaryPerDayEntries(summaryPerDayEntries);

        return ResponseEntity.of(Optional.of(numberOfSavedEntries));
    }
}
