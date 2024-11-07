package moller.solar.solarpersistence.controller;

import moller.solar.solarpersistence.service.EnergyCostService;
import moller.solarpersistence.openapi.api.EnergyCostControllerApi;
import moller.solarpersistence.openapi.model.EnergyCostEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EnergyCostController implements EnergyCostControllerApi {

    private final EnergyCostService energyCostService;

    public EnergyCostController(
            EnergyCostService energyCostService) {

        this.energyCostService = energyCostService;
    }

    @Override
    public ResponseEntity<EnergyCostEntry> createEnergyCostEntry(EnergyCostEntry energyCostEntry) {
        EnergyCostEntry createdEnergyCostEntry = energyCostService.createEnergyCostEntry(energyCostEntry);

        return ResponseEntity.of(Optional.of(createdEnergyCostEntry));
    }

    @Override
    public ResponseEntity<Integer> deleteEnergyCostEntry(Integer id) {
        return ResponseEntity.of(Optional.of(energyCostService.deleteEnergyCostEntry(id)));
    }

    @Override
    public ResponseEntity<List<EnergyCostEntry>> getAllEnergyCostEntries() {
        List<EnergyCostEntry> allEnergyCostEntries = energyCostService.getAllEnergyCostEntries();

        if (allEnergyCostEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allEnergyCostEntries));
    }
}
