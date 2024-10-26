package moller.solar.solarpersistence.service;

import moller.solar.solarpersistence.mapper.DataExportMapper;
import moller.solar.solarpersistence.persistence.entity.DataExportEntryEntity;
import moller.solar.solarpersistence.persistence.repository.DataExportRepository;
import moller.solarpersistence.openapi.model.DataExportEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DataExportService {

    private final DataExportMapper dataExportMapper;
    private final DataExportRepository dataExportRepository;

    public DataExportService(
            DataExportMapper dataExportMapper,
            DataExportRepository dataExportRepository) {

        this.dataExportMapper = dataExportMapper;
        this.dataExportRepository = dataExportRepository;
    }

    @Transactional
    public Optional<DataExportEntry> getDataExportEntryByIID(Integer iid) {
        Optional<DataExportEntryEntity> dataExportEntryEntityById = dataExportRepository.findById(iid);

        if (dataExportEntryEntityById.isPresent()) {
            return Optional.of(dataExportMapper.mapEntryToEntity(dataExportEntryEntityById.get()));
        }
        return Optional.empty();
    }

    @Transactional
    public List<DataExportEntry> findByYearAndMonth(int year, int month) {
        return dataExportMapper.mapEntityToEntry(dataExportRepository.findByYearAndMonth(year, month));
    }

    @Transactional
    public List<DataExportEntry> findByTimespanOrderedByTimestamp(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return dataExportMapper.mapEntityToEntry(dataExportRepository.findByTimespanOrderedByTimestamp(fromDateTime, toDateTime));
    }

    @Transactional
    public void importCsvFileToDatabase(List<DataExportEntry> dateExportEntries) {
        dataExportRepository.saveAll(dataExportMapper.mapEntryToEntity(dateExportEntries));
    }
}
