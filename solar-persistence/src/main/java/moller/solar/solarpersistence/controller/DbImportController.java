package moller.solar.solarpersistence.controller;

import moller.solar.solarpersistence.mapper.DataExportIdPkImportMapper;
import moller.solar.solarpersistence.persistence.entity.DataExportEntryEntity;
import moller.solar.solarpersistence.persistence.entity.DataExportIdPkEntryEntity;
import moller.solar.solarpersistence.persistence.repository.DataExportIdPkRepository;
import moller.solar.solarpersistence.persistence.repository.DataExportRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DbImportController {

    private final DataExportRepository  dataExportRepository;
    private final DataExportIdPkRepository dataExportIdPkRepository;

    private final DataExportIdPkImportMapper dataExportIdPkImportMapper;

    public DbImportController(
            DataExportRepository dataExportRepository,
            DataExportIdPkRepository dataExportIdPkRepository, DataExportIdPkImportMapper dataExportIdPkImportMapper) {

        this.dataExportRepository = dataExportRepository;
        this.dataExportIdPkRepository = dataExportIdPkRepository;
        this.dataExportIdPkImportMapper = dataExportIdPkImportMapper;
    }

    @RequestMapping(value = "/db-import", method = RequestMethod.GET)
    public void doImport() {
        long count = dataExportRepository.count();
        long count1 = dataExportIdPkRepository.count();

        System.out.println(System.currentTimeMillis() +  " dataExportRepository count = " + count);
        System.out.println(System.currentTimeMillis() +  " dataExportIdPkRepository count = " + count1);

        List<DataExportEntryEntity> all = dataExportRepository.findAll();

        List<DataExportIdPkEntryEntity> dataExportIdPkEntryEntities = dataExportIdPkImportMapper.mapEntryToEntity(all);

        dataExportIdPkRepository.saveAll(dataExportIdPkEntryEntities);

        count = dataExportRepository.count();
        count1 = dataExportIdPkRepository.count();

        System.out.println(System.currentTimeMillis() +  " dataExportRepository count = " + count);
        System.out.println(System.currentTimeMillis() +  " dataExportIdPkRepository count = " + count1);
    }
}
