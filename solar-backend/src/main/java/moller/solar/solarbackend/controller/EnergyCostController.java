package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.EnergyCostEntryDto;
import moller.solarpersistence.openapi.client.api.EnergyCostControllerApi;
import moller.solarpersistence.openapi.client.model.EnergyCostEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EnergyCostController extends AbstractV1BaseController {

    private final EnergyCostControllerApi energyCostControllerApi;

    public EnergyCostController(EnergyCostControllerApi energyCostControllerApi) {
        this.energyCostControllerApi = energyCostControllerApi;
    }

    @GetMapping(value = "/energy-costs")
    public ResponseEntity<List<EnergyCostEntry>> getAllEnergyCostEntries() {
        return ResponseEntity.of(Optional.of(energyCostControllerApi.getAllEnergyCostEntries()));
    }

    @PostMapping(value = "/energy-costs")
    public ResponseEntity<EnergyCostEntry> createEnergyCostEntry(@RequestBody EnergyCostEntryDto energyCostEntryDto) {
        EnergyCostEntry energyCostEntry = new EnergyCostEntry.Builder()
                .fromDate(energyCostEntryDto.getFromDate())
                .toDate(energyCostEntryDto.getToDate())
                .energyCostPerKwhInTenThousands(energyCostEntryDto.getEnergyCostPerKwhInTenThousands())
                .electricalGridCostInTenThousands(energyCostEntryDto.getElectricalGridCostInTenThousands())
                .feeOneInTenThousands(energyCostEntryDto.getFeeOneInTenThousands())
                .feeTwoInTenThousands(energyCostEntryDto.getFeeTwoInTenThousands())
                .feeThreeInTenThousands(energyCostEntryDto.getFeeThreeInTenThousands())
                .valueAddedTaxPercentageRateInMinorUnit(energyCostEntryDto.getValueAddedTaxPercentageRateInMinorUnit())
                .build();

        return ResponseEntity.of(Optional.of(energyCostControllerApi.createEnergyCostEntry(energyCostEntry)));
    }

    @PutMapping(value = "/energy-costs")
    public ResponseEntity<EnergyCostEntry> updateEnergyCostEntry(@RequestBody EnergyCostEntryDto energyCostEntryDto) {
        EnergyCostEntry energyCostEntry = new EnergyCostEntry.Builder()
                .id(energyCostEntryDto.getId())
                .fromDate(energyCostEntryDto.getFromDate())
                .toDate(energyCostEntryDto.getToDate())
                .energyCostPerKwhInTenThousands(energyCostEntryDto.getEnergyCostPerKwhInTenThousands())
                .electricalGridCostInTenThousands(energyCostEntryDto.getElectricalGridCostInTenThousands())
                .feeOneInTenThousands(energyCostEntryDto.getFeeOneInTenThousands())
                .feeTwoInTenThousands(energyCostEntryDto.getFeeTwoInTenThousands())
                .feeThreeInTenThousands(energyCostEntryDto.getFeeThreeInTenThousands())
                .valueAddedTaxPercentageRateInMinorUnit(energyCostEntryDto.getValueAddedTaxPercentageRateInMinorUnit())
                .build();

        return ResponseEntity.of(Optional.of(energyCostControllerApi.updateEnergyCostEntry(energyCostEntry)));
    }

    @DeleteMapping(value = "/energy-costs/{id}")
    public ResponseEntity<Integer> deleteEnergyCostEntry(@PathVariable Integer id) {
        energyCostControllerApi.deleteEnergyCostEntry(id);

        return ResponseEntity.of(Optional.of(id));
    }

    @GetMapping(value = "/energy-costs/{id}")
    public ResponseEntity<EnergyCostEntry> getEnergyCostEntry(@PathVariable Integer id) {
        EnergyCostEntry energyCostEntry = energyCostControllerApi.getEnergyCostEntry(id);

        return ResponseEntity.of(Optional.of(energyCostEntry));
    }
}
