package moller.solar.solarpersistence.delegate;

import moller.solar.solarpersistence.service.EnergySaleCompensationService;
import moller.solarpersistence.openapi.api.EnergySaleCompensationControllerApiDelegate;
import moller.solarpersistence.openapi.model.EnergySaleCompensationEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnergySaleCompensationControllerApiDelegateImpl implements EnergySaleCompensationControllerApiDelegate {

    private final EnergySaleCompensationService energySaleCompensationService;

    public EnergySaleCompensationControllerApiDelegateImpl(
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
