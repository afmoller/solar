package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.persistence.DataExportEntry;
import moller.solar.solarbackend.persistence.DataExportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class DataExportController {

    private final DataExportRepository dataExportRepository;

    public DataExportController(DataExportRepository dataExportRepository) {
        this.dataExportRepository = dataExportRepository;
    }

    @GetMapping(value = "/getDataExportEntryByIID")
    public ResponseEntity<DataExportEntry> getDataExportEntryByIID(@RequestParam("iid") Integer iid) {
        return ResponseEntity.of(dataExportRepository.findById(iid));
    }
}
