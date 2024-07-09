package moller.solar.solarbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import moller.solar.solarbackend.csv.CsvReader;
import moller.solar.solarbackend.dto.CsvImportResultDto;
import moller.solar.solarbackend.mapper.CsvRecordMapper;
import moller.solar.solarbackend.persistence.DataExportEntry;
import moller.solar.solarbackend.persistence.DataExportRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CsvController extends AbstractV1BaseController {

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
    public ResponseEntity<CsvImportResultDto> importCsvFileToDatabase(@RequestParam("csvFile") MultipartFile csvFile) throws IOException {

        Iterable<CSVRecord> csvRecords = csvReader.parseFile(new InputStreamReader(csvFile.getInputStream()));

        List<DataExportEntry> dateExportEntries = new ArrayList<>();

        CsvImportResultDto csvImportResultDto = new CsvImportResultDto();

        for (CSVRecord csvRecord : csvRecords) {
            DataExportEntry dataExportEntry = csvRecordMapper.mapToDataExportEntry(csvRecord);

            LocalDateTime timestampOfDataExportEntry = dataExportEntry.getTimestamp();
            populateTimeStampFields(csvImportResultDto, timestampOfDataExportEntry);

            dateExportEntries.add(dataExportEntry);
        }

        csvImportResultDto.setNumberOfImportedEntries(dateExportEntries.size());

        dataExportRepository.saveAll(dateExportEntries);

        return ResponseEntity.of(Optional.of(csvImportResultDto));
    }

    private void populateTimeStampFields(CsvImportResultDto csvImportResultDto, LocalDateTime timestampOfDataExportEntry) {
        if (timestampOfDataExportEntry.isBefore(csvImportResultDto.getTimestampOfFirstEntry())) {
            csvImportResultDto.setTimestampOfFirstEntry(timestampOfDataExportEntry);
        }

        if (timestampOfDataExportEntry.isAfter(csvImportResultDto.getTimestampOfLastEntry())) {
            csvImportResultDto.setTimestampOfLastEntry(timestampOfDataExportEntry);
        }
    }
}
