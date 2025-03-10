package moller.solar.solarpersistence.controller;

import moller.openapi.persistence.solar.api.ReturnOnInvestmentControllerApi;
import moller.openapi.persistence.solar.model.ReturnOnInvestmentEntry;
import moller.solar.solarpersistence.service.ReturnOnInvestmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ReturnOnInvestmentController implements ReturnOnInvestmentControllerApi {

    private final ReturnOnInvestmentService returnOnInvestmentService;

    public ReturnOnInvestmentController(
            ReturnOnInvestmentService returnOnInvestmentService) {

        this.returnOnInvestmentService = returnOnInvestmentService;
    }

    @Override
    public ResponseEntity<ReturnOnInvestmentEntry> createReturnOnInvestmentEntry(ReturnOnInvestmentEntry returnOnInvestmentEntry) {
        ReturnOnInvestmentEntry createdReturnOnInvestmentEntry = returnOnInvestmentService.createReturnOnInvestmentEntry(returnOnInvestmentEntry);

        return ResponseEntity.of(Optional.of(createdReturnOnInvestmentEntry));
    }

    @Override
    public ResponseEntity<ReturnOnInvestmentEntry> updateReturnOnInvestmentEntry(ReturnOnInvestmentEntry returnOnInvestmentEntry) {
        ReturnOnInvestmentEntry updatedReturnOnInvestmentEntry = returnOnInvestmentService.updateReturnOnInvestmentEntry(returnOnInvestmentEntry);

        return ResponseEntity.of(Optional.of(updatedReturnOnInvestmentEntry));
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

    @Override
    public ResponseEntity<ReturnOnInvestmentEntry> getReturnOnInvestmentEntry(Integer id) {
        ReturnOnInvestmentEntry returnOnInvestmentEntry = returnOnInvestmentService.getReturnOnInvestmentEntry(id);
        return ResponseEntity.of(Optional.of(returnOnInvestmentEntry));
    }
}
