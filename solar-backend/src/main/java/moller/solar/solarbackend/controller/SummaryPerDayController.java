package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.DateAndValues;
import moller.solar.solarbackend.dto.YearAndMonthProductionValues;
import moller.solar.solarbackend.enumerations.Values;
import moller.solar.solarbackend.summary.SummaryEntryValues;
import moller.solar.solarbackend.util.ValueAggregator;
import moller.solarpersistence.openapi.client.api.DataExportControllerApi;
import moller.solarpersistence.openapi.client.api.SummaryPerDayControllerApi;
import moller.solarpersistence.openapi.client.model.DataExportEntry;
import moller.solarpersistence.openapi.client.model.SummaryPerDayEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
public class SummaryPerDayController extends AbstractV1BaseController {

    private final ValueAggregator valueAggregator;

    private final DataExportControllerApi dataExportControllerApi;
    private final SummaryPerDayControllerApi summaryPerDayControllerApi;

    public SummaryPerDayController(
            ValueAggregator valueAggregator,
            DataExportControllerApi dataExportControllerApi, SummaryPerDayControllerApi summaryPerDayControllerApi) {

        this.valueAggregator = valueAggregator;
        this.dataExportControllerApi = dataExportControllerApi;
        this.summaryPerDayControllerApi = summaryPerDayControllerApi;
    }

    @PutMapping(value = "/populateSummaryPerDayForYearAndMonth")
    public ResponseEntity<Integer> populateSummaryPerDayForYearAndMonth(@RequestParam("year") Integer year, @RequestParam("month") Integer month) {

        List<DataExportEntry> dataExportEntries = dataExportControllerApi.findByYearAndMonth(year, month);
        Map<LocalDate, SummaryEntryValues> dateToSummaryEntryValues = new HashMap<>();

        dataExportEntries.forEach(dataExportEntry -> {
            dateToSummaryEntryValues.putIfAbsent(dataExportEntry.getTimestamp().toLocalDate(), new SummaryEntryValues());
            collectNonCumulatedSummaryEntryValues(dataExportEntry, dateToSummaryEntryValues);
        });

        calculateAccumulatedSummaryEntryValues(dateToSummaryEntryValues);

        List<SummaryPerDayEntry> summaryPerDayEntries = createSummaryPerDayEntries(dateToSummaryEntryValues);
        summaryPerDayControllerApi.saveSummaryPerDayEntries(summaryPerDayEntries);

        return ResponseEntity.of(Optional.of(summaryPerDayEntries.size()));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<SummaryPerDayEntry>> getAll() {
        List<SummaryPerDayEntry> allEntries = summaryPerDayControllerApi.getAllValues();
        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allEntries));
    }

    @GetMapping(value = "/getNewestEntry")
    public ResponseEntity<SummaryPerDayEntry> getNewestEntry() {
        SummaryPerDayEntry newestEntry = summaryPerDayControllerApi.getNewestEntry();

        return ResponseEntity.of(Optional.of(newestEntry));
    }

    @GetMapping(value = "/getAllAccumulatedValues")
    public ResponseEntity<DateAndValues> getAllAccumulatedValues() {
        List<SummaryPerDayEntry> allEntries = summaryPerDayControllerApi.getAllValues();
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

        LocalDate toDateStartOfMonthPlusOneMonth = toDateStartOfMonth.plusMonths(1);
        LocalDate toDate = toDateStartOfMonthPlusOneMonth.minusDays(1);

        List<SummaryPerDayEntry> allEntries = summaryPerDayControllerApi.getAllValuesForPeriod(fromDate, toDate);
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
        List<SummaryPerDayEntry> allEntries = summaryPerDayControllerApi.getAllValues();
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

        Map<Integer, Integer> monthAndLowestValue = new TreeMap<>();
        Map<Integer, Integer> monthAndHighestValue = new TreeMap<>();
        Map<Integer, Integer> monthAndAccumulatedValue = new TreeMap<>();

        valueAggregator.initiateMonthToValueMap(monthAndLowestValue, Integer.MAX_VALUE);
        valueAggregator.initiateMonthToValueMap(monthAndHighestValue, Integer.MIN_VALUE);
        valueAggregator.initiateMonthToValueMap(monthAndAccumulatedValue);

        List<Integer> monthValues = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> finalYearToMonthAndValue = yearToMonthAndValue;
        years.forEach(year -> {
            Map<Integer, Integer> monthAndValue = finalYearToMonthAndValue.get(year);
            collectValuesForCalculation(monthAndLowestValue, monthAndValue, Values.LOWEST);
            collectValuesForCalculation(monthAndHighestValue, monthAndValue, Values.HIGHEST);
            collectValuesForCalculation(monthAndAccumulatedValue, monthAndValue, Values.AVERAGE);

            monthValues.addAll(monthAndValue.values());
        });

        monthValues.addAll(monthAndLowestValue.values());
        monthValues.addAll(monthAndHighestValue.values());

        calculateAverageValue(years.size(),monthAndAccumulatedValue);
        monthValues.addAll(monthAndAccumulatedValue.values());

        years.add(-1); // Add -1 as place holder for lowest values.
        years.add(-2); // Add -2 as place holder for highest values.
        years.add(-3); // Add -3 as place holder for average values.

        YearAndMonthProductionValues values = new YearAndMonthProductionValues();
        values.setYears(years);
        values.setMonthValues(monthValues);

        return ResponseEntity.of(Optional.of(values));
    }

    private void collectValuesForCalculation(Map<Integer, Integer> resultMonthAndValue, Map<Integer, Integer> monthAndValue, Values values) {
        monthAndValue.keySet().forEach(month -> {
            switch (values) {
                case HIGHEST -> {
                    if(resultMonthAndValue.get(month) < monthAndValue.get(month)) {
                        resultMonthAndValue.put(month, monthAndValue.get(month));
                    }
                }
                case LOWEST -> {
                    if(resultMonthAndValue.get(month) > monthAndValue.get(month)) {
                        resultMonthAndValue.put(month, monthAndValue.get(month));
                    }
                }
                case AVERAGE -> {
                    resultMonthAndValue.put(month, resultMonthAndValue.get(month) + monthAndValue.get(month));
                }
            }
        });
    }

    private void  calculateAverageValue(int nrOfYears, Map<Integer, Integer> monthAndAccumulatedValue) {
        monthAndAccumulatedValue.keySet().forEach(month -> {
            monthAndAccumulatedValue.put(month, (int)((double)monthAndAccumulatedValue.get(month) / (double)3));
        });
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
        SummaryPerDayEntry entryWithHighestAccumulatedValues = summaryPerDayControllerApi.getEntryWithHighestAccumulatedValues();
        if (entryWithHighestAccumulatedValues == null) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(entryWithHighestAccumulatedValues));
    }

    private void calculateAccumulatedSummaryEntryValues(Map<LocalDate, SummaryEntryValues> dateToSummaryEntryValues) {
        SummaryPerDayEntry entryWithHighestAccumulatedValues = summaryPerDayControllerApi.getEntryWithHighestAccumulatedValues();

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
