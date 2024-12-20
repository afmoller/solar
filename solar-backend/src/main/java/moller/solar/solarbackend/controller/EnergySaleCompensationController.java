package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.EnergySaleCompensationEntryDto;
import moller.solarpersistence.openapi.client.api.EnergySaleCompensationControllerApi;
import moller.solarpersistence.openapi.client.model.EnergySaleCompensationEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EnergySaleCompensationController extends AbstractV1BaseController {

    private final EnergySaleCompensationControllerApi energySaleCompensationControllerApi;

    public EnergySaleCompensationController(EnergySaleCompensationControllerApi energySaleCompensationControllerApi) {
        this.energySaleCompensationControllerApi = energySaleCompensationControllerApi;
    }

    @GetMapping(value = "/energy-sale-compensations")
    public ResponseEntity<List<EnergySaleCompensationEntry>> getAllEnergySaleCompensationEntries() {
        return ResponseEntity.of(Optional.of(energySaleCompensationControllerApi.getAllEnergySaleCompensationEntries()));
    }

    @PostMapping(value = "/energy-sale-compensations")
    public ResponseEntity<EnergySaleCompensationEntry> createEnergySaleCompensationEntry(@RequestBody EnergySaleCompensationEntryDto energySaleCompensationEntryDto) {
        EnergySaleCompensationEntry energySaleCompensationEntry = new EnergySaleCompensationEntry.Builder()
                .compensationDate(energySaleCompensationEntryDto.getCompensationDate())
                .productionFromDate(energySaleCompensationEntryDto.getProductionFromDate())
                .productionToDate(energySaleCompensationEntryDto.getProductionToDate())
                .compensationAmountInMinorUnit(energySaleCompensationEntryDto.getCompensationAmountInMinorUnit())
                .productionYear(energySaleCompensationEntryDto.getProductionYear())
                .build();

        return ResponseEntity.of(Optional.of(energySaleCompensationControllerApi.createEnergySaleCompensationEntry(energySaleCompensationEntry)));
    }

    @PutMapping(value = "/energy-sale-compensations")
    public ResponseEntity<EnergySaleCompensationEntry> updateEnergySaleCompensationEntry(@RequestBody EnergySaleCompensationEntryDto energySaleCompensationEntryDto) {
        EnergySaleCompensationEntry energySaleCompensationEntry = new EnergySaleCompensationEntry.Builder()
                .id(energySaleCompensationEntryDto.getId())
                .compensationDate(energySaleCompensationEntryDto.getCompensationDate())
                .productionFromDate(energySaleCompensationEntryDto.getProductionFromDate())
                .productionToDate(energySaleCompensationEntryDto.getProductionToDate())
                .compensationAmountInMinorUnit(energySaleCompensationEntryDto.getCompensationAmountInMinorUnit())
                .productionYear(energySaleCompensationEntryDto.getProductionYear())
                .build();

        return ResponseEntity.of(Optional.of(energySaleCompensationControllerApi.updateEnergySaleCompensationEntry(energySaleCompensationEntry)));
    }

    @DeleteMapping(value = "/energy-sale-compensations/{id}")
    public ResponseEntity<Integer> deleteEnergySaleCompensationEntry(@PathVariable Integer id) {
        energySaleCompensationControllerApi.deleteEnergySaleCompensationEntry(id);

        return ResponseEntity.of(Optional.of(id));
    }

    @GetMapping(value = "/energy-sale-compensations/{id}")
    public ResponseEntity<EnergySaleCompensationEntry> getEnergySaleCompensationEntry(@PathVariable Integer id) {
        EnergySaleCompensationEntry energySaleCompensationEntry = energySaleCompensationControllerApi.getEnergySaleCompensationEntry(id);

        return ResponseEntity.of(Optional.of(energySaleCompensationEntry));
    }
}
