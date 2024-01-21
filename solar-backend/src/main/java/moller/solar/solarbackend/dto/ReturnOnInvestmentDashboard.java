package moller.solar.solarbackend.dto;

import moller.solar.solarbackend.persistence.ReturnOnInvestmentEntry;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReturnOnInvestmentDashboard {

    private List<ReturnOnInvestmentDashboardEntryDto> returnOnInvestmentDashboardEntryDtos;

    private int totalCost;
    private int totalIncome;

    private List<LocalDate> dates;
    private List<Double> numberOfYearsUntilPaidEntries;

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
        return numberOfYearsUntilPaidEntries;
    }

    public void initMembers(List<ReturnOnInvestmentEntry> returnOnInvestmentEntries) {

        LocalDate firstDate = null;
        int firstCost = Integer.MIN_VALUE;

        dates = new ArrayList<>();
        numberOfYearsUntilPaidEntries = new ArrayList<>();

        returnOnInvestmentDashboardEntryDtos = new ArrayList<>();

        ReturnOnInvestmentDashboardEntryDto previousEntry = null;

        for (ReturnOnInvestmentEntry returnOnInvestmentEntry : returnOnInvestmentEntries) {

            LocalDate date = returnOnInvestmentEntry.getDate();
            if (firstDate == null) {
                firstDate = date;
            }

            Integer amountInMinorUnit = returnOnInvestmentEntry.getAmountInMinorUnit();
            if (firstCost == Integer.MIN_VALUE) {
                firstCost = amountInMinorUnit;
            }

            dates.add(date);

            int deltaSinceBegin = getDeltaSinceBegin(returnOnInvestmentEntry, previousEntry);

            ReturnOnInvestmentDashboardEntryDto returnOnInvestmentDashboardEntryDto = new ReturnOnInvestmentDashboardEntryDto();
            returnOnInvestmentDashboardEntryDto.setDate(date);
            returnOnInvestmentDashboardEntryDto.setId(returnOnInvestmentEntry.getId());
            returnOnInvestmentDashboardEntryDto.setDescription(returnOnInvestmentEntry.getDescription());
            returnOnInvestmentDashboardEntryDto.setAmountInMinorUnit(amountInMinorUnit);
            returnOnInvestmentDashboardEntryDto.setAmountIsPositive(returnOnInvestmentEntry.getAmountIsPositive());
            returnOnInvestmentDashboardEntryDto.setSaldo(getSaldo(returnOnInvestmentEntry, previousEntry));
            returnOnInvestmentDashboardEntryDto.setDeltaSinceBegin(deltaSinceBegin);

            double numberOfYearsUntilPaid = computeNumberOfYearsUntilPaid(firstDate, firstCost, deltaSinceBegin, returnOnInvestmentEntry);

            returnOnInvestmentDashboardEntryDto.setNumberOfYearsUntilPaid(numberOfYearsUntilPaid);
            numberOfYearsUntilPaidEntries.add(numberOfYearsUntilPaid);

            if (returnOnInvestmentEntry.getAmountIsPositive()) {
                totalIncome += amountInMinorUnit;
            } else {
                totalCost += amountInMinorUnit;
            }

            returnOnInvestmentDashboardEntryDtos.add(returnOnInvestmentDashboardEntryDto);
            previousEntry = returnOnInvestmentDashboardEntryDto;
        }
    }

    private double computeNumberOfYearsUntilPaid(LocalDate firstDate, int firstCost, int deltaSinceBegin, ReturnOnInvestmentEntry returnOnInvestmentEntry) {
        long daysBetween = ChronoUnit.DAYS.between(firstDate, returnOnInvestmentEntry.getDate());

        return  ((double)(daysBetween * firstCost)) / ((double)deltaSinceBegin) / 365.25d;
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
