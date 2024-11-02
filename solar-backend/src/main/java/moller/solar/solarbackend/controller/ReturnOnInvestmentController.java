package moller.solar.solarbackend.controller;

import moller.solar.solarbackend.dto.ReturnOnInvestmentDashboard;
import moller.solar.solarbackend.dto.ReturnOnInvestmentEntryDto;
import moller.solarpersistence.openapi.client.api.ReturnOnInvestmentControllerApi;
import moller.solarpersistence.openapi.client.model.ReturnOnInvestmentEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReturnOnInvestmentController extends AbstractV1BaseController {

    private final ReturnOnInvestmentControllerApi returnOnInvestmentControllerApi;

    public ReturnOnInvestmentController(ReturnOnInvestmentControllerApi returnOnInvestmentControllerApi) {
        this.returnOnInvestmentControllerApi = returnOnInvestmentControllerApi;
    }

    @GetMapping(value = "/return-on-investments")
    public ResponseEntity<List<ReturnOnInvestmentEntry>> getAllReturnOnInvestmentEntries() {
        return ResponseEntity.of(Optional.of(returnOnInvestmentControllerApi.getAllReturnOnInvestmentEntries()));
    }

    @GetMapping(value = "/return-on-investment-dashboard")
    public ResponseEntity<ReturnOnInvestmentDashboard> getReturnOnInvestmentDashboard() {
        List< ReturnOnInvestmentEntry> all = returnOnInvestmentControllerApi.getAllReturnOnInvestmentEntries();

        ReturnOnInvestmentDashboard returnOnInvestmentDashboard = new ReturnOnInvestmentDashboard();
        returnOnInvestmentDashboard.initMembers(all);

        return ResponseEntity.of(Optional.of(returnOnInvestmentDashboard));
    }

    @PostMapping(value = "/return-on-investments")
    public ResponseEntity<ReturnOnInvestmentEntry> createReturnOnInvestmentEntry(@RequestBody ReturnOnInvestmentEntryDto returnOnInvestmentEntryDto) {
        ReturnOnInvestmentEntry returnOnInvestmentEntry = new ReturnOnInvestmentEntry.Builder()
                .date(returnOnInvestmentEntryDto.getDate())
                .description(returnOnInvestmentEntryDto.getDescription())
                .amountIsPositive(returnOnInvestmentEntryDto.getAmountIsPositive())
                .amountInMinorUnit(returnOnInvestmentEntryDto.getAmountInMinorUnit())
                .build();

        return ResponseEntity.of(Optional.of(returnOnInvestmentControllerApi.createReturnOnInvestmentEntry(returnOnInvestmentEntry)));
    }

    @DeleteMapping(value = "/return-on-investments/{id}")
    public ResponseEntity<Integer> deleteReturnOnInvestmentEntry(@PathVariable Integer id) {
        returnOnInvestmentControllerApi.deleteReturnOnInvestmentEntry(id);

        return ResponseEntity.of(Optional.of(id));
    }
}
