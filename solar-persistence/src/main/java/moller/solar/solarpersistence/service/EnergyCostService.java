package moller.solar.solarpersistence.service;

import moller.openapi.persistence.solar.model.EnergyCostEntry;
import moller.solar.solarpersistence.mapper.EnergyCostMapper;
import moller.solar.solarpersistence.persistence.entity.EnergyCostEntryEntity;
import moller.solar.solarpersistence.persistence.repository.EnergyCostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnergyCostService {

    private final EnergyCostMapper energyCostMapper;
    private final EnergyCostRepository energyCostRepository;

    public EnergyCostService(
            EnergyCostMapper energyCostMapper,
            EnergyCostRepository energyCostRepository) {

        this.energyCostMapper = energyCostMapper;
        this.energyCostRepository = energyCostRepository;
    }

    @Transactional
    public List<EnergyCostEntry> getAllEnergyCostEntries() {
        List<EnergyCostEntryEntity> allSortedByDate = energyCostRepository.findAll(Sort.by("fromDate", "id").ascending());

        return energyCostMapper.map(allSortedByDate);
    }

    @Transactional
    public EnergyCostEntry createEnergyCostEntry(EnergyCostEntry energyCostEntry) {
        return saveEnergyCostEntry(energyCostEntry);
    }

    @Transactional
    public EnergyCostEntry updateEnergyCostEntry(EnergyCostEntry energyCostEntry) {
        return saveEnergyCostEntry(energyCostEntry);
    }

    @Transactional
    public Integer deleteEnergyCostEntry(Integer id) {
        energyCostRepository.deleteById(id);

        return id;
    }

    @Transactional
    public EnergyCostEntry getEnergyCostEntry(Integer id) {
        EnergyCostEntryEntity referenceById = energyCostRepository.getReferenceById(id);
        return energyCostMapper.map(referenceById);
    }

    private EnergyCostEntry saveEnergyCostEntry(EnergyCostEntry energyCostEntryToSave) {
        EnergyCostEntryEntity savedEnergyCostEntryEntity = energyCostRepository.save(energyCostMapper.map(energyCostEntryToSave));

        return energyCostMapper.map(savedEnergyCostEntryEntity);
    }
}
