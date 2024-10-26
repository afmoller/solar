package moller.solar.solarpersistence.service;

import moller.solar.solarpersistence.mapper.SummaryPerDayMapper;
import moller.solar.solarpersistence.persistence.entity.SummaryPerDayEntryEntity;
import moller.solar.solarpersistence.persistence.repository.SummaryPerDayRepository;
import moller.solarpersistence.openapi.model.SummaryPerDayEntry;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SummaryPerDayService {

    private final SummaryPerDayMapper summaryPerDayMapper;
    private final SummaryPerDayRepository summaryPerDayRepository;

    public SummaryPerDayService(
            SummaryPerDayMapper summaryPerDayMapper,
            SummaryPerDayRepository summaryPerDayRepository) {

        this.summaryPerDayMapper = summaryPerDayMapper;
        this.summaryPerDayRepository = summaryPerDayRepository;
    }

    @Transactional
    public Integer saveSummaryPerDayEntries(List<SummaryPerDayEntry> summaryPerDayEntries) {
        List<SummaryPerDayEntryEntity> savedSummaryPerDayEntries = summaryPerDayRepository.saveAll(summaryPerDayMapper.mapEntriesToEntities(summaryPerDayEntries));

        return savedSummaryPerDayEntries.size();
    }

    @Transactional
    public SummaryPerDayEntry getNewestEntry() {
        SummaryPerDayEntryEntity newestEntry = summaryPerDayRepository.getNewestEntry();

        return summaryPerDayMapper.map(newestEntry);
    }

    @Transactional
    public List<SummaryPerDayEntry> getAllValues() {
        List<SummaryPerDayEntryEntity> allEntries = summaryPerDayRepository.findAll(Sort.by(Sort.Order.asc("date")));

        return summaryPerDayMapper.map(allEntries);
    }

    @Transactional
    public List<SummaryPerDayEntry> getAllValuesForPeriod(LocalDate fromDate, LocalDate toDate) {
        List<SummaryPerDayEntryEntity> allEntries = summaryPerDayRepository.getAllValuesForPeriod(fromDate, toDate);

        return summaryPerDayMapper.map(allEntries);
    }

    @Transactional
    public SummaryPerDayEntry getEntryWithHighestAccumulatedValues() {
        SummaryPerDayEntryEntity entryWithHighestAccumulatedValues = summaryPerDayRepository.findEntryWithHighestAccumulatedValues();

        return summaryPerDayMapper.map(entryWithHighestAccumulatedValues);
    }
}
