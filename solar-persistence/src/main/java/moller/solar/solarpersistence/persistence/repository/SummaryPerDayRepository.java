package moller.solar.solarpersistence.persistence.repository;

import moller.solar.solarpersistence.dto.DateAndValues;
import moller.solar.solarpersistence.persistence.entity.SummaryPerDayEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SummaryPerDayRepository extends JpaRepository<SummaryPerDayEntryEntity, LocalDateTime> {

    @Query(value = "SELECT s FROM SummaryPerDayEntryEntity s WHERE s.accumulatedConsumptionWattHours = (SELECT MAX(s1.accumulatedConsumptionWattHours) FROM SummaryPerDayEntryEntity s1)")
    SummaryPerDayEntryEntity findEntryWithHighestAccumulatedValues();

    @Query(value = "SELECT s.date, s.accumulatedSaleWattHours, s.accumulatedPurchaseWattHours, s.accumulatedProductionWattHours, s.accumulatedConsumptionWattHours, s.accumulatedSelfConsumptionWattHours FROM SummaryPerDayEntryEntity s ORDER BY s.date")
    List<DateAndValues> getAllAccumulatedValues();

    @Query(value = "SELECT s FROM SummaryPerDayEntryEntity s WHERE s.date between :fromDate AND :toDate ORDER BY s.date")
    List<SummaryPerDayEntryEntity> getAllValuesForPeriod(LocalDate fromDate, LocalDate toDate);

    @Query(value = "SELECT s FROM SummaryPerDayEntryEntity s ORDER BY s.date DESC LIMIT 1")
    SummaryPerDayEntryEntity getNewestEntry();
}
