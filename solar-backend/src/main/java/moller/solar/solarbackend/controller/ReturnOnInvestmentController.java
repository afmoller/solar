package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.ReturnOnInvestmentDashboard;
import moller.solar.solarbackend.dto.ReturnOnInvestmentEntryDto;
import moller.solar.solarbackend.persistence.ReturnOnInvestmentEntry;
import moller.solar.solarbackend.persistence.ReturnOnInvestmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class ReturnOnInvestmentController {

    private final ReturnOnInvestmentRepository returnOnInvestmentRepository;

    public ReturnOnInvestmentController(ReturnOnInvestmentRepository returnOnInvestmentRepository) {
        this.returnOnInvestmentRepository = returnOnInvestmentRepository;
    }

    @GetMapping(value = "/return-on-investments")
    public ResponseEntity<List<ReturnOnInvestmentEntry>> getAllReturnOnInvestmentEntries() {
        return ResponseEntity.of(Optional.of(returnOnInvestmentRepository.findAll(Sort.by("date", "id").ascending())));
    }

    @GetMapping(value = "/return-on-investment-dashboard")
    public ResponseEntity<ReturnOnInvestmentDashboard> getReturnOnInvestmentDashboard() {
        List<ReturnOnInvestmentEntry> all = returnOnInvestmentRepository.findAll(Sort.by("date", "id").ascending());

        ReturnOnInvestmentDashboard returnOnInvestmentDashboard = new ReturnOnInvestmentDashboard();
        returnOnInvestmentDashboard.initMembers(all);

        return ResponseEntity.of(Optional.of(returnOnInvestmentDashboard));
    }

    @PostMapping(value = "/return-on-investments")
    public ResponseEntity<ReturnOnInvestmentEntry> createReturnOnInvestmentEntry(@RequestBody ReturnOnInvestmentEntryDto returnOnInvestmentEntryDto) {
        ReturnOnInvestmentEntry returnOnInvestmentEntry = new ReturnOnInvestmentEntry.ReturnOnInvestmentEntryBuilder()
                .setDate(returnOnInvestmentEntryDto.getDate())
                .setDescription(returnOnInvestmentEntryDto.getDescription())
                .setAmountIsPositive(returnOnInvestmentEntryDto.getAmountIsPositive())
                .setAmountInMinorUnit(returnOnInvestmentEntryDto.getAmountInMinorUnit())
                .build();

        return ResponseEntity.of(Optional.of(returnOnInvestmentRepository.save(returnOnInvestmentEntry)));
    }

    @DeleteMapping(value = "/return-on-investments/{id}")
    public ResponseEntity<Integer> deleteReturnOnInvestmentEntry(@PathVariable Integer id) {

        returnOnInvestmentRepository.deleteById(id);

        return ResponseEntity.of(Optional.of(id));
    }
}
