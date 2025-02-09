package moller.solar.solarpersistence.service;

import moller.openapi.persistence.solar.model.EnergySaleCompensationEntry;
import moller.solar.solarpersistence.mapper.EnergySaleCompensationMapper;
import moller.solar.solarpersistence.persistence.entity.EnergySaleCompensationEntryEntity;
import moller.solar.solarpersistence.persistence.repository.EnergySaleCompensationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnergySaleCompensationService {

    private final EnergySaleCompensationMapper energySaleCompensationMapper;
    private final EnergySaleCompensationRepository energySaleCompensationRepository;

    public EnergySaleCompensationService(
            EnergySaleCompensationMapper energySaleCompensationMapper,
            EnergySaleCompensationRepository energySaleCompensationRepository) {

        this.energySaleCompensationMapper = energySaleCompensationMapper;
        this.energySaleCompensationRepository = energySaleCompensationRepository;
    }

    @Transactional
    public List<EnergySaleCompensationEntry> getAllEnergySaleCompensationEntries() {
        List<EnergySaleCompensationEntryEntity> allSortedByCompensationDate = energySaleCompensationRepository.findAll(Sort.by("compensationDate", "id").ascending());

        return energySaleCompensationMapper.map(allSortedByCompensationDate);
    }

    @Transactional
    public EnergySaleCompensationEntry createEnergySaleCompensationEntry(EnergySaleCompensationEntry energySaleCompensationEntry) {
        return saveEnergySaleCompensationEntry(energySaleCompensationEntry);
    }

    @Transactional
    public EnergySaleCompensationEntry updateEnergySaleCompensationEntry(EnergySaleCompensationEntry energySaleCompensationEntry) {
        return saveEnergySaleCompensationEntry(energySaleCompensationEntry);
    }

    @Transactional
    public Integer deleteEnergySaleCompensationEntry(Integer id) {
        energySaleCompensationRepository.deleteById(id);

        return id;
    }

    @Transactional
    public EnergySaleCompensationEntry getEnergySaleCompensationEntry(Integer id) {
        EnergySaleCompensationEntryEntity referenceById = energySaleCompensationRepository.getReferenceById(id);
        return energySaleCompensationMapper.map(referenceById);
    }

    private EnergySaleCompensationEntry saveEnergySaleCompensationEntry(EnergySaleCompensationEntry energySaleCompensationEntryToSave) {
        EnergySaleCompensationEntryEntity savedEnergySaleCompensationEntryEntity = energySaleCompensationRepository.save(energySaleCompensationMapper.map(energySaleCompensationEntryToSave));

        return energySaleCompensationMapper.map(savedEnergySaleCompensationEntryEntity);
    }
}
