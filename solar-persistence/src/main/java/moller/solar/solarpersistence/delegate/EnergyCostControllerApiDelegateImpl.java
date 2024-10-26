package moller.solar.solarpersistence.delegate;

import moller.solar.solarpersistence.service.EnergyCostService;
import moller.solarpersistence.openapi.api.EnergyCostControllerApiDelegate;
import moller.solarpersistence.openapi.model.EnergyCostEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnergyCostControllerApiDelegateImpl implements EnergyCostControllerApiDelegate {

    private final EnergyCostService energyCostService;

    public EnergyCostControllerApiDelegateImpl(
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
