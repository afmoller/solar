package moller.solar.solarpersistence.service;

import moller.openapi.persistence.solar.model.DataExportEntry;
import moller.solar.solarpersistence.mapper.DataExportIdPkMapper;
import moller.solar.solarpersistence.persistence.entity.DataExportIdPkEntryEntity;
import moller.solar.solarpersistence.persistence.repository.DataExportIdPkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DataExportService {

    private final DataExportIdPkMapper dataExportMapper;
    private final DataExportIdPkRepository dataExportRepository;

    public DataExportService(
            DataExportIdPkMapper dataExportMapper,
            DataExportIdPkRepository dataExportRepository) {

        this.dataExportMapper = dataExportMapper;
        this.dataExportRepository = dataExportRepository;
    }

    @Transactional
    public Optional<DataExportEntry> getDataExportEntryByIID(Integer idPk) {
        Optional<DataExportIdPkEntryEntity> dataExportEntryEntityById = dataExportRepository.findById(idPk);

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
