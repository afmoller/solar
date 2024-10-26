package moller.solar.solarpersistence.service;

import moller.solar.solarpersistence.mapper.ReturnOnInvestementMapper;
import moller.solar.solarpersistence.persistence.entity.ReturnOnInvestmentEntryEntity;
import moller.solar.solarpersistence.persistence.repository.ReturnOnInvestmentRepository;
import moller.solarpersistence.openapi.model.ReturnOnInvestmentEntry;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReturnOnInvestmentService {

    private final ReturnOnInvestementMapper returnOnInvestementMapper;
    private final ReturnOnInvestmentRepository returnOnInvestmentRepository;

    public ReturnOnInvestmentService(
            ReturnOnInvestementMapper returnOnInvestementMapper,
            ReturnOnInvestmentRepository returnOnInvestmentRepository) {

        this.returnOnInvestementMapper = returnOnInvestementMapper;
        this.returnOnInvestmentRepository = returnOnInvestmentRepository;
    }

    @Transactional
    public List<ReturnOnInvestmentEntry> getAllReturnOnInvestmentEntries() {
        List<ReturnOnInvestmentEntryEntity> allSortedByDate = returnOnInvestmentRepository.findAll(Sort.by("date", "id").ascending());

        return returnOnInvestementMapper.map(allSortedByDate);
    }

    @Transactional
    public List<ReturnOnInvestmentEntry> getReturnOnInvestmentDashboard() {
        return getAllReturnOnInvestmentEntries();
    }

    @Transactional
    public ReturnOnInvestmentEntry createReturnOnInvestmentEntry(ReturnOnInvestmentEntry returnOnInvestmentEntryEntity) {
        ReturnOnInvestmentEntryEntity savedReturnOnInvestmentEntryEntity = returnOnInvestmentRepository.save(returnOnInvestementMapper.map(returnOnInvestmentEntryEntity));

        return returnOnInvestementMapper.map(savedReturnOnInvestmentEntryEntity);
    }

    @Transactional
    public Integer deleteReturnOnInvestmentEntry(Integer id) {
        returnOnInvestmentRepository.deleteById(id);

        return id;
    }
}
