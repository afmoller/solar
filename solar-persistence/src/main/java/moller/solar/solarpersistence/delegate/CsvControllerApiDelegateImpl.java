package moller.solar.solarpersistence.delegate;

import jakarta.validation.Valid;
import moller.solar.solarpersistence.service.DataExportService;
import moller.solarpersistence.openapi.api.CsvControllerApiDelegate;
import moller.solarpersistence.openapi.model.DataExportEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvControllerApiDelegateImpl implements CsvControllerApiDelegate {

    private final DataExportService dataExportService;

    public CsvControllerApiDelegateImpl(
            DataExportService dataExportService) {

        this.dataExportService = dataExportService;
    }

    @Override
    public ResponseEntity<Void> importCsvFileToDatabase(List<@Valid DataExportEntry> dateExportEntries) {
        dataExportService.importCsvFileToDatabase(dateExportEntries);

        return ResponseEntity.ok().build();
    }
}
