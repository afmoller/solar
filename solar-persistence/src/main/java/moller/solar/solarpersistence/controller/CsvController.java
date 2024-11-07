package moller.solar.solarpersistence.controller;

import jakarta.validation.Valid;
import moller.solar.solarpersistence.service.DataExportService;
import moller.solarpersistence.openapi.api.CsvControllerApi;
import moller.solarpersistence.openapi.model.DataExportEntry;
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
    public ResponseEntity<Void> importCsvFileToDatabase(List<@Valid DataExportEntry> dateExportEntries) {
        dataExportService.importCsvFileToDatabase(dateExportEntries);

        return ResponseEntity.ok().build();
    }
}
