package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.DateAndValues;
import moller.solar.solarbackend.dto.YearAndMonthProductionValues;
import moller.solar.solarbackend.persistence.DataExportEntry;
import moller.solar.solarbackend.persistence.DataExportRepository;
import moller.solar.solarbackend.persistence.SummaryPerDayEntry;
import moller.solar.solarbackend.persistence.SummaryPerDayRepository;
import moller.solar.solarbackend.summary.SummaryEntryValues;
import moller.solar.solarbackend.util.ValueAggregator;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class SummaryPerDayController {

    private final ValueAggregator valueAggregator;
    private final DataExportRepository dataExportRepository;
    private final SummaryPerDayRepository summaryPerDayRepository;

    public SummaryPerDayController(
            ValueAggregator valueAggregator,
            DataExportRepository dataExportRepository,
            SummaryPerDayRepository summaryPerDayRepository) {

        this.valueAggregator = valueAggregator;
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

        return ResponseEntity.of(Optional.of(summaryPerDayEntries.size()));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<SummaryPerDayEntry>> getAll() {
        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.findAll(Sort.by(Sort.Order.asc("date")));
        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allEntries));
    }

    @GetMapping(value = "/getNewestEntry")
    public ResponseEntity<SummaryPerDayEntry> getNewestEntry() {
        SummaryPerDayEntry newestEntry = summaryPerDayRepository.getNewestEntry();

        return ResponseEntity.of(Optional.of(newestEntry));
    }

    @GetMapping(value = "/getAllAccumulatedValues")
    public ResponseEntity<DateAndValues> getAllAccumulatedValues() {
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

        DateAndValues dateAndValues = DateAndValues.createDateAndValues(
                date,
                accumulatedConsumptionWattHours,
                accumulatedProductionWattHours,
                accumulatedPurchaseWattHours,
                accumulatedSelfConsumptionWattHours,
                accumulatedSaleWattHours);

        return ResponseEntity.of(Optional.of(dateAndValues));
    }


    @GetMapping(value = "/getAllValuesForPeriod")
    public ResponseEntity<DateAndValues> getAllValuesForPeriod(@RequestParam LocalDate selectedFromDate, @RequestParam LocalDate selectedToDate) {

        int yearFrom = selectedFromDate.getYear();
        int yearTo = selectedToDate.getYear();

        int monthFrom = selectedFromDate.getMonthValue();
        int monthTo = selectedToDate.getMonthValue();

        LocalDate fromDate = LocalDate.of(yearFrom, monthFrom, 1);

        LocalDate toDateStartOfMonth = LocalDate.of(yearTo, monthTo,1);

        LocalDate toDateStartOfMonthMinusOneDay = toDateStartOfMonth.minusDays(1);
        LocalDate toDate = toDateStartOfMonthMinusOneDay.plusMonths(1);


        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.getAllValuesForPeriod(fromDate, toDate);
        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        List<LocalDate> date = new ArrayList<>();
        List<Integer> consumptionWattHours = new ArrayList<>();
        List<Integer> productionWattHours = new ArrayList<>();
        List<Integer> purchaseWattHours = new ArrayList<>();
        List<Integer> selfConsumptionWattHours = new ArrayList<>();
        List<Integer> saleWattHours = new ArrayList<>();

        allEntries.forEach(summaryPerDayEntry -> {
            date.add(summaryPerDayEntry.getDate());
            consumptionWattHours.add(summaryPerDayEntry.getConsumptionWattHours());
            productionWattHours.add(summaryPerDayEntry.getProductionWattHours());
            purchaseWattHours.add(summaryPerDayEntry.getPurchaseWattHours());
            selfConsumptionWattHours.add(summaryPerDayEntry.getSelfConsumptionWattHours());
            saleWattHours.add(summaryPerDayEntry.getSaleWattHours());
        });

        DateAndValues dateAndValues = DateAndValues.createDateAndValues(
                date,
                consumptionWattHours,
                productionWattHours,
                purchaseWattHours,
                selfConsumptionWattHours,
                saleWattHours);

        return ResponseEntity.of(Optional.of(dateAndValues));
    }


    @GetMapping(value = "/getAggregatedMonthValues")
    public ResponseEntity<YearAndMonthProductionValues> getAggregatedMonthValues(@RequestParam String valueType) {
        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.findAll(Sort.by(Sort.Order.asc("date")));
        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        Map<Integer, Map<Integer, Integer>> yearToMonthAndValue = new TreeMap<>();

        switch (valueType) {
            case "production":
                yearToMonthAndValue = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getProductionWattHours);
                break;
            case "consumption":
                yearToMonthAndValue = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getConsumptionWattHours);
                break;
            case "purchase":
                yearToMonthAndValue = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getPurchaseWattHours);
                break;
            case "sale":
                yearToMonthAndValue = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getSaleWattHours);
                break;
            case "selfconsumption":
                yearToMonthAndValue = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getSelfConsumptionWattHours);
                break;
            case "autarky":
                Map<Integer, Map<Integer, Integer>> yearToMonthAndValueConsumption = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getConsumptionWattHours);
                Map<Integer, Map<Integer, Integer>> yearToMonthAndValueSelfConsumption = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getSelfConsumptionWattHours);

                yearToMonthAndValue = calculateAutarkyValues(
                        yearToMonthAndValueConsumption,
                        yearToMonthAndValueSelfConsumption
                );
                break;
        }

        List<Integer> years = new ArrayList<>();
        years.addAll(yearToMonthAndValue.keySet().stream().sorted().toList());
        YearAndMonthProductionValues values = new YearAndMonthProductionValues();
        values.setYears(years);

        List<Integer> monthValues = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> finalYearToMonthAndValue = yearToMonthAndValue;
        years.forEach(year -> {
            Map<Integer, Integer> monthAndValue = finalYearToMonthAndValue.get(year);
            monthValues.addAll(monthAndValue.values());
        });

        values.setMonthValues(monthValues);

        return ResponseEntity.of(Optional.of(values));
    }

    private Map<Integer, Map<Integer, Integer>> calculateAutarkyValues(Map<Integer, Map<Integer, Integer>> yearToMonthAndValueConsumption, Map<Integer, Map<Integer, Integer>> yearToMonthAndValueSelfConsumption) {

        Map<Integer, Map<Integer, Integer>> returnValue = new TreeMap<>();

        for (Integer year : yearToMonthAndValueConsumption.keySet()) {
            Map<Integer, Integer> monthAndValueConsumption = yearToMonthAndValueConsumption.get(year);
            Map<Integer, Integer> monthAndValueSelfConsumption = yearToMonthAndValueSelfConsumption.get(year);

            returnValue.put(year, new TreeMap<>());

            for (Integer month : monthAndValueConsumption.keySet()) {
                Integer valueConsumption = monthAndValueConsumption.get(month);
                Integer valueSelfConsumption = monthAndValueSelfConsumption.get(month);

                Integer autarky = Long.valueOf(Math.round( (valueSelfConsumption.doubleValue() / valueConsumption.doubleValue()) * 100)).intValue();

                Map<Integer, Integer> monthToValue = returnValue.get(year);
                monthToValue.put(month, autarky);
            }
        }
        return returnValue;
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
                .toList();

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
}
