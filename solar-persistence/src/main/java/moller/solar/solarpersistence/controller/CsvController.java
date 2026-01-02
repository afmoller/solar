package moller.solar.solarpersistence.controller;

import moller.openapi.persistence.solar.api.CsvControllerApi;
import moller.openapi.persistence.solar.model.DataExportEntry;
import moller.solar.solarpersistence.service.DataExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CsvController implements CsvControllerApi {

    private final DataExportService dataExportService;

    public CsvController(
            DataExportService dataExportService) {

        this.dataExportService = dataExportService;
    }

    @Override
    public ResponseEntity<Void> importCsvFileToDatabase(List<DataExportEntry> dateExportEntries) {
        dataExportService.importCsvFileToDatabase(dateExportEntries);

        return ResponseEntity.ok().build();
    }
}
