package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.EnergySaleCompensationEntryDto;
import moller.solar.solarbackend.persistence.EnergySaleCompensationEntry;
import moller.solar.solarbackend.persistence.EnergySaleCompensationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class EnergySaleCompensationController {

    private final EnergySaleCompensationRepository energySaleCompensationRepository;

    public EnergySaleCompensationController(EnergySaleCompensationRepository energySaleCompensationRepository) {
        this.energySaleCompensationRepository = energySaleCompensationRepository;
    }

    @GetMapping(value = "/energy-sale-compensations")
    public ResponseEntity<List<EnergySaleCompensationEntry>> getAllEnergySaleCompensationEntries() {
        return ResponseEntity.of(Optional.of(energySaleCompensationRepository.findAll(Sort.by("compensationDate", "id").ascending())));
    }

//    @GetMapping(value = "/return-on-investment-dashboard")
//    public ResponseEntity<ReturnOnInvestmentDashboard> getReturnOnInvestmentDashboard() {
//        List<ReturnOnInvestmentEntry> all = energySaleCompensationRepository.findAll(Sort.by("date", "id").ascending());
//
//        ReturnOnInvestmentDashboard returnOnInvestmentDashboard = new ReturnOnInvestmentDashboard();
//        returnOnInvestmentDashboard.initMembers(all);
//
//        return ResponseEntity.of(Optional.of(returnOnInvestmentDashboard));
//    }

    @PostMapping(value = "/energy-sale-compensations")
    public ResponseEntity<EnergySaleCompensationEntry> createEnergySaleCompensationEntry(@RequestBody EnergySaleCompensationEntryDto energySaleCompensationEntryDto) {
        EnergySaleCompensationEntry returnOnInvestmentEntry = new EnergySaleCompensationEntry.EnergySaleCompensationEntryBuilder()
                .setCompensationDate(energySaleCompensationEntryDto.getCompensationDate())
                .setProductionFromDate(energySaleCompensationEntryDto.getProductionFrom())
                .setProductionToDate(energySaleCompensationEntryDto.getProductionTo())
                .setCompensationAmountInMinorUnit(energySaleCompensationEntryDto.getCompensationAmountInMinorUnit())
                .setProductionYear(energySaleCompensationEntryDto.getProductionYear())
                .build();

        return ResponseEntity.of(Optional.of(energySaleCompensationRepository.save(returnOnInvestmentEntry)));
    }

    @DeleteMapping(value = "/energy-sale-compensations/{id}")
    public ResponseEntity<Integer> deleteEnergySaleCompensationEntry(@PathVariable Integer id) {

        energySaleCompensationRepository.deleteById(id);

        return ResponseEntity.of(Optional.of(id));
    }
}
