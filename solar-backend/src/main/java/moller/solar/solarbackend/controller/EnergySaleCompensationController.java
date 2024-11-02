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
    public ResponseEntity<List<moller.solarpersistence.openapi.client.model.EnergySaleCompensationEntry>> getAllEnergySaleCompensationEntries() {
        return ResponseEntity.of(Optional.of(energySaleCompensationControllerApi.getAllEnergySaleCompensationEntries()));
    }

    @PostMapping(value = "/energy-sale-compensations")
    public ResponseEntity<EnergySaleCompensationEntry> createEnergySaleCompensationEntry(@RequestBody EnergySaleCompensationEntryDto energySaleCompensationEntryDto) {
        moller.solarpersistence.openapi.client.model.EnergySaleCompensationEntry energySaleCompensationEntry = new moller.solarpersistence.openapi.client.model.EnergySaleCompensationEntry.Builder()
                .compensationDate(energySaleCompensationEntryDto.getCompensationDate())
                .productionFromDate(energySaleCompensationEntryDto.getProductionFrom())
                .productionToDate(energySaleCompensationEntryDto.getProductionTo())
                .compensationAmountInMinorUnit(energySaleCompensationEntryDto.getCompensationAmountInMinorUnit())
                .productionYear(energySaleCompensationEntryDto.getProductionYear())
                .build();

        return ResponseEntity.of(Optional.of(energySaleCompensationControllerApi.createEnergySaleCompensationEntry(energySaleCompensationEntry)));
    }

    @DeleteMapping(value = "/energy-sale-compensations/{id}")
    public ResponseEntity<Integer> deleteEnergySaleCompensationEntry(@PathVariable Integer id) {
        energySaleCompensationControllerApi.deleteEnergySaleCompensationEntry(id);

        return ResponseEntity.of(Optional.of(id));
    }
}
