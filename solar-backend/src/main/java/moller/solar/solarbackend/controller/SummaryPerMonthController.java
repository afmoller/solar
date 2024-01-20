package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.DateAndValues;
import moller.solar.solarbackend.dto.SummaryPerMonthEntry;
import moller.solar.solarbackend.persistence.SummaryPerDayEntry;
import moller.solar.solarbackend.persistence.SummaryPerDayRepository;
import moller.solar.solarbackend.util.ValueAggregator;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class SummaryPerMonthController {

    private final ValueAggregator valueAggregator;
    private final SummaryPerDayRepository summaryPerDayRepository;


    public SummaryPerMonthController(
            SummaryPerDayRepository summaryPerDayRepository,
            ValueAggregator valueAggregator) {

        this.valueAggregator = valueAggregator;
        this.summaryPerDayRepository = summaryPerDayRepository;
    }

    @GetMapping(value = "/getMonthlyAccumulatedValues")
    public ResponseEntity<List<SummaryPerMonthEntry>> getMonthlyAccumulatedValues() {

        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.findAll(Sort.by(Sort.Order.asc("date")));

        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        List<SummaryPerMonthEntry> allSummaryPerMonthEntries = getSummaryPerMonthEntries(allEntries);
        return ResponseEntity.of(Optional.of(allSummaryPerMonthEntries));
    }

    @GetMapping(value = "/getMonthlyAccumulatedValuesForPeriod")
    public ResponseEntity<DateAndValues> getMonthlyAccumulatedValuesForPeriod(@RequestParam LocalDate selectedFromDate, @RequestParam LocalDate selectedToDate) {
        LocalDate fromDateAdjustedToStartOfMonth = selectedFromDate.withDayOfMonth(1);
        LocalDate toDateAdjustedToEndOfMonth =  YearMonth.of(selectedToDate.getYear(), selectedToDate.getMonthValue()).atEndOfMonth();


        List<SummaryPerDayEntry> allEntries = summaryPerDayRepository.getAllValuesForPeriod(fromDateAdjustedToStartOfMonth, toDateAdjustedToEndOfMonth);

        if (allEntries == null || allEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        List<SummaryPerMonthEntry> allSummaryPerMonthEntries = getSummaryPerMonthEntries(allEntries);

        List<LocalDate> date = new ArrayList<>();
        List<Integer> consumptionWattHours = new ArrayList<>();
        List<Integer> productionWattHours = new ArrayList<>();
        List<Integer> purchaseWattHours = new ArrayList<>();
        List<Integer> selfConsumptionWattHours = new ArrayList<>();
        List<Integer> saleWattHours = new ArrayList<>();

        allSummaryPerMonthEntries.forEach(summaryPerMonthEntry -> {
            LocalDate yearAndMonth = LocalDate.of(summaryPerMonthEntry.getYearOfEntry(), summaryPerMonthEntry.getMonthOfEntry(), 1);

            if (yearAndMonth.compareTo(fromDateAdjustedToStartOfMonth) >= 0 && yearAndMonth.compareTo(toDateAdjustedToEndOfMonth) <= 0) {
                date.add(yearAndMonth);
                consumptionWattHours.add(summaryPerMonthEntry.getConsumptionWattHours());
                productionWattHours.add(summaryPerMonthEntry.getProductionWattHours());
                purchaseWattHours.add(summaryPerMonthEntry.getPurchaseWattHours());
                selfConsumptionWattHours.add(summaryPerMonthEntry.getSelfConsumptionWattHours());
                saleWattHours.add(summaryPerMonthEntry.getSaleWattHours());
            }
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

    private List<SummaryPerMonthEntry> getSummaryPerMonthEntries(List<SummaryPerDayEntry> allEntries) {
        Map<Integer, Map<Integer, Integer>> onMonthSaleWattHours = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getSaleWattHours);
        Map<Integer, Map<Integer, Integer>> onMonthPurchaseWattHours = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getPurchaseWattHours);
        Map<Integer, Map<Integer, Integer>> onMonthProductionWattHours = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getProductionWattHours);
        Map<Integer, Map<Integer, Integer>> onMonthConsumptionWattHours = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getConsumptionWattHours);
        Map<Integer, Map<Integer, Integer>> onMonthSelfConsumptionWattHours = valueAggregator.aggregateOnMonth(allEntries, SummaryPerDayEntry::getSelfConsumptionWattHours);

        List<SummaryPerMonthEntry> allSummaryPerMonthEntries = new ArrayList<>();

        for (Integer year : onMonthSaleWattHours.keySet()) {
            Map<Integer, Integer> monthValueSaleWattHours = onMonthSaleWattHours.get(year);
            Map<Integer, Integer> monthValuePurchaseWattHours = onMonthPurchaseWattHours.get(year);
            Map<Integer, Integer> monthValueProductionWattHours = onMonthProductionWattHours.get(year);
            Map<Integer, Integer> monthValueConsumptionWattHours = onMonthConsumptionWattHours.get(year);
            Map<Integer, Integer> monthValueSelfConsumptionWattHours = onMonthSelfConsumptionWattHours.get(year);

            for (Integer month : monthValueSaleWattHours.keySet()) {
                Integer monthValueSelfConsumption = monthValueSelfConsumptionWattHours.get(month);
                Integer monthValueConsumption = monthValueConsumptionWattHours.get(month);

                allSummaryPerMonthEntries.add(new SummaryPerMonthEntry.SummaryPerMonthEntryBuilder()
                        .setMonthOfEntry(month)
                        .setYearOfEntry(year)
                        .setAutarchy((monthValueSelfConsumption.doubleValue() / monthValueConsumption.doubleValue()) * 100)
                        .setConsumptionWattHours(monthValueConsumption)
                        .setProductionWattHours(monthValueProductionWattHours.get(month))
                        .setPurchaseWattHours(monthValuePurchaseWattHours.get(month))
                        .setSaleWattHours(monthValueSaleWattHours.get(month))
                        .setSelfConsumptionWattHours(monthValueSelfConsumption)
                        .build());
            }
        }
        return allSummaryPerMonthEntries;
    }
}
