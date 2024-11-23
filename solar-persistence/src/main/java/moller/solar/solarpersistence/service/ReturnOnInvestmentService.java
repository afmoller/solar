package moller.solar.solarpersistence.service;

import moller.solar.solarpersistence.mapper.ReturnOnInvestmentMapper;
import moller.solar.solarpersistence.persistence.entity.ReturnOnInvestmentEntryEntity;
import moller.solar.solarpersistence.persistence.repository.ReturnOnInvestmentRepository;
import moller.solarpersistence.openapi.model.ReturnOnInvestmentEntry;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReturnOnInvestmentService {

    private final ReturnOnInvestmentMapper returnOnInvestmentMapper;
    private final ReturnOnInvestmentRepository returnOnInvestmentRepository;

    public ReturnOnInvestmentService(
            ReturnOnInvestmentMapper returnOnInvestmentMapper,
            ReturnOnInvestmentRepository returnOnInvestmentRepository) {

        this.returnOnInvestmentMapper = returnOnInvestmentMapper;
        this.returnOnInvestmentRepository = returnOnInvestmentRepository;
    }

    @Transactional
    public List<ReturnOnInvestmentEntry> getAllReturnOnInvestmentEntries() {
        List<ReturnOnInvestmentEntryEntity> allSortedByDate = returnOnInvestmentRepository.findAll(Sort.by("date", "id").ascending());

        return returnOnInvestmentMapper.map(allSortedByDate);
    }

    @Transactional
    public List<ReturnOnInvestmentEntry> getReturnOnInvestmentDashboard() {
        return getAllReturnOnInvestmentEntries();
    }

    @Transactional
    public ReturnOnInvestmentEntry createReturnOnInvestmentEntry(ReturnOnInvestmentEntry returnOnInvestmentEntry) {
        return saveReturnOnInvestmentEntry(returnOnInvestmentEntry);
    }

    @Transactional
    public ReturnOnInvestmentEntry updateReturnOnInvestmentEntry(ReturnOnInvestmentEntry returnOnInvestmentEntry) {
        return saveReturnOnInvestmentEntry(returnOnInvestmentEntry);
    }

    @Transactional
    public Integer deleteReturnOnInvestmentEntry(Integer id) {
        returnOnInvestmentRepository.deleteById(id);

        return id;
    }

    @Transactional
    public ReturnOnInvestmentEntry getReturnOnInvestmentEntry(Integer id) {
        ReturnOnInvestmentEntryEntity referenceById = returnOnInvestmentRepository.getReferenceById(id);
        return returnOnInvestmentMapper.map(referenceById);
    }

    private ReturnOnInvestmentEntry saveReturnOnInvestmentEntry(ReturnOnInvestmentEntry returnOnInvestmentEntryEntityToSave) {
        ReturnOnInvestmentEntryEntity savedReturnOnInvestmentEntryEntity = returnOnInvestmentRepository.save(returnOnInvestmentMapper.map(returnOnInvestmentEntryEntityToSave));

        return returnOnInvestmentMapper.map(savedReturnOnInvestmentEntryEntity);
    }
}
