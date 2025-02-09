package moller.solar.solarpersistence.controller;

import moller.openapi.persistence.solar.api.EnergyCostControllerApi;
import moller.openapi.persistence.solar.model.EnergyCostEntry;
import moller.solar.solarpersistence.service.EnergyCostService;
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
    public ResponseEntity<EnergyCostEntry> updateEnergyCostEntry(EnergyCostEntry energyCostEntry) {
        EnergyCostEntry updatedEnergyCostEntry = energyCostService.updateEnergyCostEntry(energyCostEntry);

        return ResponseEntity.of(Optional.of(updatedEnergyCostEntry));
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

    @Override
    public ResponseEntity<EnergyCostEntry> getEnergyCostEntry(Integer id) {
        EnergyCostEntry energyCostEntry = energyCostService.getEnergyCostEntry(id);
        return ResponseEntity.of(Optional.of(energyCostEntry));
    }


}
