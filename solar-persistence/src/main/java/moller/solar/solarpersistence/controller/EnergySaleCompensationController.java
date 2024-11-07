package moller.solar.solarpersistence.controller;

import moller.solar.solarpersistence.service.EnergySaleCompensationService;
import moller.solarpersistence.openapi.api.EnergySaleCompensationControllerApi;
import moller.solarpersistence.openapi.model.EnergySaleCompensationEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EnergySaleCompensationController implements EnergySaleCompensationControllerApi {

    private final EnergySaleCompensationService energySaleCompensationService;

    public EnergySaleCompensationController(
            EnergySaleCompensationService energySaleCompensationService) {

        this.energySaleCompensationService = energySaleCompensationService;
    }

    @Override
    public ResponseEntity<EnergySaleCompensationEntry> createEnergySaleCompensationEntry(EnergySaleCompensationEntry energySaleCompensationEntry) {
        EnergySaleCompensationEntry createdEnergySaleCompensationEntry = energySaleCompensationService.createEnergySaleCompensationEntry(energySaleCompensationEntry);

        return ResponseEntity.of(Optional.of(createdEnergySaleCompensationEntry));
    }

    @Override
    public ResponseEntity<Integer> deleteEnergySaleCompensationEntry(Integer id) {
        return ResponseEntity.of(Optional.of(energySaleCompensationService.deleteEnergySaleCompensationEntry(id)));
    }

    @Override
    public ResponseEntity<List<EnergySaleCompensationEntry>> getAllEnergySaleCompensationEntries() {
        List<EnergySaleCompensationEntry> allEnergySaleCompensationEntries = energySaleCompensationService.getAllEnergySaleCompensationEntries();

        if (allEnergySaleCompensationEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allEnergySaleCompensationEntries));
    }
}
