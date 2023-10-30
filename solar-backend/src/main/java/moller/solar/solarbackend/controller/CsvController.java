package moller.solar.solarbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import moller.solar.solarbackend.csv.CsvReader;
import moller.solar.solarbackend.mapper.CsvRecordMapper;
import moller.solar.solarbackend.persistence.DataExportEntry;
import moller.solar.solarbackend.persistence.DataExportRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CsvController {

    private final CsvReader csvReader;
    private final CsvRecordMapper csvRecordMapper;
    private final DataExportRepository dataExportRepository;

    public CsvController(
            CsvReader csvReader,
            CsvRecordMapper csvRecordMapper,
            DataExportRepository dataExportRepository) {

        this.csvReader = csvReader;
        this.csvRecordMapper = csvRecordMapper;
        this.dataExportRepository = dataExportRepository;
    }

    @PostMapping(value = "/importCsvFileToDatabase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(requestBody = @RequestBody(content = @Content(encoding = @Encoding(name = "csvFile", contentType = "text/csv"))))
    public void importCsvFileToDatabase(@RequestParam("csvFile") MultipartFile csvFile) throws IOException {

        Iterable<CSVRecord> csvRecords = csvReader.parseFile(new InputStreamReader(csvFile.getInputStream()));

        List<DataExportEntry> dateExportEntries = new ArrayList<>();

        for (CSVRecord csvRecord : csvRecords) {
            dateExportEntries.add(csvRecordMapper.mapToDataExportEntry(csvRecord));
        }
        dataExportRepository.saveAll(dateExportEntries);
    }
}
