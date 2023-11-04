package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.DateAndAccumulatedValues;
import moller.solar.solarbackend.persistence.DataExportRepository;
import moller.solar.solarbackend.persistence.SummaryPerDayEntry;
import moller.solar.solarbackend.persistence.SummaryPerDayRepository;
import moller.solar.solarbackend.util.ValueAggregator;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SummaryPerMonthController {

    private final ValueAggregator valueAggregator;
    private final SummaryPerDayRepository summaryPerDayRepository;


    public SummaryPerMonthController(
            DataExportRepository dataExportRepository,
            SummaryPerDayRepository summaryPerDayRepository, ValueAggregator valueAggregator) {

        this.valueAggregator = valueAggregator;
        this.summaryPerDayRepository = summaryPerDayRepository;
    }

    @GetMapping(value = "/getMonthlyAccumulatedValues")
    public ResponseEntity<DateAndAccumulatedValues> getMonthlyAccumulatedValues() {
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

        DateAndAccumulatedValues dateAndAccumulatedValues = DateAndAccumulatedValues.createDateAndAccumulatedValues(
                date,
                accumulatedConsumptionWattHours,
                accumulatedProductionWattHours,
                accumulatedPurchaseWattHours,
                accumulatedSelfConsumptionWattHours,
                accumulatedSaleWattHours);

        return ResponseEntity.of(Optional.of(dateAndAccumulatedValues));
    }
}
