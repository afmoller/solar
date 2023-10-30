package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.DateAndAccumulatedValues;
import moller.solar.solarbackend.dto.YearAndMonthProductionValues;
import moller.solar.solarbackend.persistence.DataExportEntry;
import moller.solar.solarbackend.persistence.DataExportRepository;
import moller.solar.solarbackend.persistence.SummaryPerDayEntry;
import moller.solar.solarbackend.persistence.SummaryPerDayRepository;
import moller.solar.solarbackend.summary.SummaryEntryValues;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class SummaryPerDayController {

    private final DataExportRepository dataExportRepository;
    private final SummaryPerDayRepository summaryPerDayRepository;

    public SummaryPerDayController(DataExportRepository dataExportRepository, SummaryPerDayRepository summaryPerDayRepository) {
        this.dataExportRepository = dataExportRepository;
        this.summaryPerDayRepository = summaryPerDayRepository;
    }

    @PutMapping(value = "/populateSummaryPerDayForYearAndMonth")
    public ResponseEntity<Integer> populateSummaryPerDayForYearAndMonth(@RequestParam("year") Integer year, @RequestParam("month") Integer month) {

        List<DataExportEntry> dataExportEntries = dataExportRepository.findByYearAndMonth(year, month);

        Map<LocalDate, SummaryEntryValues> dateToSummaryEntryValues = new HashMap<>();

        dataExportEntries.forEach(dataExportEntry -> {
            dateToSummaryEntryValues.putIfAbsent(dataExportEntry.getTimestamp().toLocalDate(), new SummaryEntryValues());
            collectNonCumulatedSummaryEntryValues(dataExportEntry, dateToSummaryEntryValues);
        });

        calculateAccumulatedSummaryEntryValues(dateToSummaryEntryValues);

        List<SummaryPerDayEntry> summaryPerDayEntries = createSummaryPerDayEntries(dateToSummaryEntryValues);
        summaryPerDayRepository.saveAll(summaryPerDayEntries);

        return ResponseEntity.of(Optional.of(Integer.valueOf(summaryPerDayEntries.size())));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<SummaryPerDayEntry>> getAll() {
        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.findAll(Sort.by(Sort.Order.asc("date")));
        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allEntries));
    }

    @GetMapping(value = "/getAllAccumulatedValues")
    public ResponseEntity<DateAndAccumulatedValues> getAllAccumulatedValues() {
        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.findAll(Sort.by(Sort.Order.asc("date")));
        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        List<LocalDate> date = new ArrayList<>();
        List<Integer> accumulatedConsumptionWattHours = new ArrayList<>();
        List<Integer> accumulatedProductionWattHours = new ArrayList<>();
        List<Integer> accumulatedPurchaseWattHours = new ArrayList<>();
        List<Integer> accumulatedSelfConsumptionWattHours = new ArrayList<>();
        List<Integer> accumulatedSaleWattHours = new ArrayList<>();

        allEntries.forEach(summaryPerDayEntry -> {
            date.add(summaryPerDayEntry.getDate());
            accumulatedConsumptionWattHours.add(summaryPerDayEntry.getAccumulatedConsumptionWattHours());
            accumulatedProductionWattHours.add(summaryPerDayEntry.getAccumulatedProductionWattHours());
            accumulatedPurchaseWattHours.add(summaryPerDayEntry.getAccumulatedPurchaseWattHours());
            accumulatedSelfConsumptionWattHours.add(summaryPerDayEntry.getAccumulatedSelfConsumptionWattHours());
            accumulatedSaleWattHours.add(summaryPerDayEntry.getAccumulatedSaleWattHours());
        });

        DateAndAccumulatedValues dateAndAccumulatedValues = createDateAndAccumulatedValues(
                date,
                accumulatedConsumptionWattHours,
                accumulatedProductionWattHours,
                accumulatedPurchaseWattHours,
                accumulatedSelfConsumptionWattHours,
                accumulatedSaleWattHours);

        return ResponseEntity.of(Optional.of(dateAndAccumulatedValues));
    }

    @GetMapping(value = "/getAggregatedMonthValues")
    public ResponseEntity<YearAndMonthProductionValues> getAggregatedMonthValues() {
        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.findAll(Sort.by(Sort.Order.asc("date")));
        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        Map<Integer, Map<Integer, Integer>> yearToMonthAndValue = new TreeMap<>();

        allEntries.forEach(summaryPerDayEntry -> {
            Integer yearOfEntry = summaryPerDayEntry.getYearOfEntry();
            Integer monthOfEntry = summaryPerDayEntry.getMonthOfEntry();
            Integer productionWattHours = summaryPerDayEntry.getProductionWattHours();

            Map<Integer, Integer> monthToValue = yearToMonthAndValue.get(yearOfEntry);
            if (monthToValue == null) {
                monthToValue = new TreeMap<>();
                initateMonthToValueMap(monthToValue);
                yearToMonthAndValue.put(yearOfEntry, monthToValue);
            }

            monthToValue.put(monthOfEntry, monthToValue.get(monthOfEntry) + productionWattHours);
        });

        List<Integer> years = new ArrayList<>();
        years.addAll(yearToMonthAndValue.keySet().stream().sorted().toList());
        YearAndMonthProductionValues values = new YearAndMonthProductionValues();
        values.setYears(years);

        List<Integer> monthValues = new ArrayList<>();
        years.forEach(year -> {
            Map<Integer, Integer> monthAndValue = yearToMonthAndValue.get(year);
            monthValues.addAll(monthAndValue.values());
        });

        values.setMonthValues(monthValues);

        return ResponseEntity.of(Optional.of(values));
    }

    private void initateMonthToValueMap(Map<Integer, Integer> mapToInitiate) {
        for (int i = 1; i <= 12; i++) {
            mapToInitiate.put(i, 0);
        }
    }

    @GetMapping(value = "/findEntryWithHighestAccumulatedValues")
    public ResponseEntity<SummaryPerDayEntry> getEntryWithHighestAccumulatedValues() {
        SummaryPerDayEntry entryWithHighestAccumulatedValues = summaryPerDayRepository.findEntryWithHighestAccumulatedValues();
        if (entryWithHighestAccumulatedValues == null) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(entryWithHighestAccumulatedValues));
    }

    private void calculateAccumulatedSummaryEntryValues(Map<LocalDate, SummaryEntryValues> dateToSummaryEntryValues) {
        SummaryPerDayEntry entryWithHighestAccumulatedValues = summaryPerDayRepository.findEntryWithHighestAccumulatedValues();

        AtomicInteger currentValueAccumulatedSaleWattHours = new AtomicInteger(entryWithHighestAccumulatedValues == null ? 0 : entryWithHighestAccumulatedValues.getAccumulatedSaleWattHours());
        AtomicInteger currentValueAccumulatedPurchaseWattHours = new AtomicInteger(entryWithHighestAccumulatedValues == null ? 0 : entryWithHighestAccumulatedValues.getAccumulatedPurchaseWattHours());
        AtomicInteger currentValueAccumulatedProductionWattHours = new AtomicInteger(entryWithHighestAccumulatedValues == null ? 0 : entryWithHighestAccumulatedValues.getAccumulatedProductionWattHours());
        AtomicInteger currentValueAccumulatedConsumptionWattHours = new AtomicInteger(entryWithHighestAccumulatedValues == null ? 0 : entryWithHighestAccumulatedValues.getAccumulatedConsumptionWattHours());
        AtomicInteger currentValueAccumulatedSelfConsumptionWattHours = new AtomicInteger(entryWithHighestAccumulatedValues == null ? 0 : entryWithHighestAccumulatedValues.getAccumulatedSelfConsumptionWattHours());

        List<LocalDate> datesSortedFromEarliestToLatest = dateToSummaryEntryValues
                .keySet()
                .stream()
                .sorted(LocalDate::compareTo)
                .collect(Collectors.toList());

        datesSortedFromEarliestToLatest.forEach(date -> {
            SummaryEntryValues summaryEntryValuesForDate = dateToSummaryEntryValues.get(date);
            currentValueAccumulatedSaleWattHours.set(summaryEntryValuesForDate.setAccumulatedSaleWattHours(currentValueAccumulatedSaleWattHours));
            currentValueAccumulatedPurchaseWattHours.set(summaryEntryValuesForDate.setAccumulatedPurchaseWattHours(currentValueAccumulatedPurchaseWattHours));
            currentValueAccumulatedProductionWattHours.set(summaryEntryValuesForDate.setAccumulatedProductionWattHours(currentValueAccumulatedProductionWattHours));
            currentValueAccumulatedConsumptionWattHours.set(summaryEntryValuesForDate.setAccumulatedConsumptionWattHours(currentValueAccumulatedConsumptionWattHours));
            currentValueAccumulatedSelfConsumptionWattHours.set(summaryEntryValuesForDate.setAccumulatedSelfConsumptionWattHours(currentValueAccumulatedSelfConsumptionWattHours));
        });
    }

    private void collectNonCumulatedSummaryEntryValues(DataExportEntry dataExportEntry, Map<LocalDate, SummaryEntryValues> dateToSummaryEntryValues) {
        SummaryEntryValues summaryEntryValues = dateToSummaryEntryValues.get(dataExportEntry.getTimestamp().toLocalDate());
        summaryEntryValues.addNonCumulatedValues(dataExportEntry);
    }

    private List<SummaryPerDayEntry> createSummaryPerDayEntries(Map<LocalDate, SummaryEntryValues> dateToSummaryEntryValues) {
        return dateToSummaryEntryValues
                .entrySet()
                .stream()
                .map(SummaryPerDayController::mapToSummaryPerDayEntry)
                .collect(Collectors.toList());
    }

    private static SummaryPerDayEntry mapToSummaryPerDayEntry(Map.Entry<LocalDate, SummaryEntryValues> entry) {
        return entry.getValue().mapToSummaryPerDayEntry(entry.getKey());
    }

    public DateAndAccumulatedValues createDateAndAccumulatedValues(
            List<LocalDate> date,
            List<Integer> accumulatedConsumptionWattHours,
            List<Integer> accumulatedProductionWattHours,
            List<Integer> accumulatedPurchaseWattHours,
            List<Integer> accumulatedSelfConsumptionWattHours,
            List<Integer> accumulatedSaleWattHours) {

        return new DateAndAccumulatedValues.DateAndAccumulatedValuesBuilder()
                .setDate(date)
                .setAccumulatedConsumptionWattHours(accumulatedConsumptionWattHours)
                .setAccumulatedProductionWattHours(accumulatedProductionWattHours)
                .setAccumulatedPurchaseWattHours(accumulatedPurchaseWattHours)
                .setAccumulatedSelfConsumptionWattHours(accumulatedSelfConsumptionWattHours)
                .setAccumulatedSaleWattHours(accumulatedSaleWattHours)
                .build();
    }
}
