package moller.solar.solarbackend.persistence;

import moller.solar.solarbackend.dto.DateAndValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SummaryPerDayRepository extends JpaRepository<SummaryPerDayEntry, LocalDateTime> {

    @Query(value = "SELECT s FROM SummaryPerDayEntry s WHERE s.accumulatedConsumptionWattHours = (SELECT MAX(s1.accumulatedConsumptionWattHours) FROM SummaryPerDayEntry s1)")
    SummaryPerDayEntry findEntryWithHighestAccumulatedValues();

    @Query(value = "SELECT s.date, s.accumulatedSaleWattHours, s.accumulatedPurchaseWattHours, s.accumulatedProductionWattHours, s.accumulatedConsumptionWattHours, s.accumulatedSelfConsumptionWattHours FROM SummaryPerDayEntry s ORDER BY s.date")
    List<DateAndValues> getAllAccumulatedValues();

    @Query(value = "SELECT s FROM SummaryPerDayEntry s WHERE s.date between :fromDate AND :toDate ORDER BY s.date")
    List<SummaryPerDayEntry> getAllValuesForPeriod(LocalDate fromDate, LocalDate toDate);

    @Query(value = "SELECT s FROM SummaryPerDayEntry s ORDER BY s.date DESC LIMIT 1")
    SummaryPerDayEntry getNewestEntry();
}
