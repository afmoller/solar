package moller.solar.solarpersistence.delegate;

import moller.solar.solarpersistence.service.ReturnOnInvestmentService;
import moller.solarpersistence.openapi.api.ReturnOnInvestmentControllerApiDelegate;
import moller.solarpersistence.openapi.model.ReturnOnInvestmentEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReturnOnInvestmentControllerApiDelegateImpl implements ReturnOnInvestmentControllerApiDelegate {

    private final ReturnOnInvestmentService returnOnInvestmentService;

    public ReturnOnInvestmentControllerApiDelegateImpl(
            ReturnOnInvestmentService returnOnInvestmentService) {

        this.returnOnInvestmentService = returnOnInvestmentService;
    }

    @Override
    public ResponseEntity<ReturnOnInvestmentEntry> createReturnOnInvestmentEntry(ReturnOnInvestmentEntry returnOnInvestmentEntry) {
        ReturnOnInvestmentEntry createdReturnOnInvestmentEntry = returnOnInvestmentService.createReturnOnInvestmentEntry(returnOnInvestmentEntry);

        return ResponseEntity.of(Optional.of(createdReturnOnInvestmentEntry));
    }

    @Override
    public ResponseEntity<Integer> deleteReturnOnInvestmentEntry(Integer id) {
        return ResponseEntity.of(Optional.of(returnOnInvestmentService.deleteReturnOnInvestmentEntry(id)));
    }

    @Override
    public ResponseEntity<List<ReturnOnInvestmentEntry>> getAllReturnOnInvestmentEntries() {
        List<ReturnOnInvestmentEntry> allReturnOnInvestmentEntries = returnOnInvestmentService.getAllReturnOnInvestmentEntries();

        if (allReturnOnInvestmentEntries.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(allReturnOnInvestmentEntries));
    }

    @Override
    public ResponseEntity<List<ReturnOnInvestmentEntry>> getReturnOnInvestmentDashboard() {
        List<ReturnOnInvestmentEntry> returnOnInvestmentDashboard = returnOnInvestmentService.getReturnOnInvestmentDashboard();

        if (returnOnInvestmentDashboard.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        return ResponseEntity.of(Optional.of(returnOnInvestmentDashboard));
    }
}
