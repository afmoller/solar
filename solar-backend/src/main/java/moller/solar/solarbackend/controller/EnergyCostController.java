package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.EnergyCostEntryDto;
import moller.solar.solarbackend.persistence.EnergyCostEntry;
import moller.solar.solarbackend.persistence.EnergyCostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class EnergyCostController {

    private final EnergyCostRepository energyCostRepository;

    public EnergyCostController(EnergyCostRepository energyCostRepository) {
        this.energyCostRepository = energyCostRepository;
    }

    @GetMapping(value = "/energy-costs")
    public ResponseEntity<List<EnergyCostEntry>> getAllEnergyCostEntries() {
        return ResponseEntity.of(Optional.of(energyCostRepository.findAll(Sort.by("fromDate", "id").ascending())));
    }

    @PostMapping(value = "/energy-costs")
    public ResponseEntity<EnergyCostEntry> createEnergyCostEntry(@RequestBody EnergyCostEntryDto energyCostEntryDto) {
        EnergyCostEntry energyCostEntry = new EnergyCostEntry.EnergyCostEntryBuilder()
                .setFromDate(energyCostEntryDto.getFromDate())
                .setToDate(energyCostEntryDto.getToDate())
                .setEnergyCostPerKwhInTenThousands(energyCostEntryDto.getEnergyCostPerKwhInTenThousands())
                .setElectricalGridCostInTenThousands(energyCostEntryDto.getElectricalGridCostInTenThousands())
                .setFeeOneInTenThousands(energyCostEntryDto.getFeeOneInTenThousands())
                .setFeeTwoInTenThousands(energyCostEntryDto.getFeeTwoInTenThousands())
                .setFeeThreeInTenThousands(energyCostEntryDto.getFeeThreeInTenThousands())
                .setValueAddedTaxPercentageRateInMinorUnit(energyCostEntryDto.getValueAddedTaxPercentageRateInMinorUnit())
                .build();

        return ResponseEntity.of(Optional.of(energyCostRepository.save(energyCostEntry)));
    }

    @DeleteMapping(value = "/energy-costs/{id}")
    public ResponseEntity<Integer> deleteEnergyCostEntry(@PathVariable Integer id) {

        energyCostRepository.deleteById(id);

        return ResponseEntity.of(Optional.of(id));
    }
}
