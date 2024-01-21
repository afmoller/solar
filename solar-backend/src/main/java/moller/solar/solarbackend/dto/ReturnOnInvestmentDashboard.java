package moller.solar.solarbackend.dto;

import moller.solar.solarbackend.persistence.ReturnOnInvestmentEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnOnInvestmentDashboard {

    private List<ReturnOnInvestmentDashboardEntryDto> returnOnInvestmentDashboardEntryDtos;

    private int totalCost;
    private int totalIncome;

    private List<LocalDate> dates;
    private List<Double> numberOfYearsUntilPaid;

    public List<ReturnOnInvestmentDashboardEntryDto> getReturnOnInvestmentDashboardEntryDtos() {
        return returnOnInvestmentDashboardEntryDtos;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public List<Double> getNumberOfYearsUntilPaid() {
        return numberOfYearsUntilPaid;
    }

    public void initMembers(List<ReturnOnInvestmentEntry> returnOnInvestmentEntries) {

        dates = new ArrayList<>();
        numberOfYearsUntilPaid = new ArrayList<>();

        returnOnInvestmentDashboardEntryDtos = new ArrayList<>();

        ReturnOnInvestmentDashboardEntryDto previousEntry = null;

        for (ReturnOnInvestmentEntry returnOnInvestmentEntry : returnOnInvestmentEntries) {
            LocalDate date = returnOnInvestmentEntry.getDate();
            dates.add(date);

            ReturnOnInvestmentDashboardEntryDto returnOnInvestmentDashboardEntryDto = new ReturnOnInvestmentDashboardEntryDto();
            returnOnInvestmentDashboardEntryDto.setDate(date);
            returnOnInvestmentDashboardEntryDto.setId(returnOnInvestmentEntry.getId());
            returnOnInvestmentDashboardEntryDto.setDescription(returnOnInvestmentEntry.getDescription());
            returnOnInvestmentDashboardEntryDto.setAmountInMinorUnit(returnOnInvestmentEntry.getAmountInMinorUnit());
            returnOnInvestmentDashboardEntryDto.setAmountIsPositive(returnOnInvestmentEntry.getAmountIsPositive());
            returnOnInvestmentDashboardEntryDto.setSaldo(getSaldo(returnOnInvestmentEntry, previousEntry));
            returnOnInvestmentDashboardEntryDto.setDeltaSinceBegin(getDeltaSinceBegin(returnOnInvestmentEntry, previousEntry));
            //returnOnInvestmentDashboardEntryDto.setNumberOfYearsUntilPaid();

            Integer amountInMinorUnit = returnOnInvestmentEntry.getAmountInMinorUnit();

            if (returnOnInvestmentEntry.getAmountIsPositive()) {
                totalIncome += amountInMinorUnit;
            } else {
                totalCost += amountInMinorUnit;
            }

            returnOnInvestmentDashboardEntryDtos.add(returnOnInvestmentDashboardEntryDto);
            previousEntry = returnOnInvestmentDashboardEntryDto;
        }
    }

    private int getDeltaSinceBegin(ReturnOnInvestmentEntry returnOnInvestmentEntry, ReturnOnInvestmentDashboardEntryDto previousDashboardEntry) {
        if (previousDashboardEntry == null) {
            return 0;
        } else {
            return previousDashboardEntry.getDeltaSinceBegin() + getAmountSigned(returnOnInvestmentEntry);
        }
    }

    private int getSaldo(ReturnOnInvestmentEntry currentEntry, ReturnOnInvestmentDashboardEntryDto previousDashboardEntry) {
        int currentAmount = getAmountSigned(currentEntry);

        if (previousDashboardEntry != null) {
            return currentAmount + previousDashboardEntry.getSaldo();
        }
        return currentAmount;
    }

    private int getAmountSigned(ReturnOnInvestmentEntry entry) {
        return entry.getAmountInMinorUnit() * (entry.getAmountIsPositive() ? 1 : -1);
    }
}
