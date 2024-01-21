package moller.solar.solarbackend.dto;

public class ReturnOnInvestmentDashboardEntryDto extends ReturnOnInvestmentEntryDto {

    private int saldo;
    private int deltaSinceBegin;

    private double numberOfYearsUntilPaid;

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getDeltaSinceBegin() {
        return deltaSinceBegin;
    }

    public void setDeltaSinceBegin(int deltaSinceBegin) {
        this.deltaSinceBegin = deltaSinceBegin;
    }

    public double getNumberOfYearsUntilPaid() {
        return numberOfYearsUntilPaid;
    }

    public void setNumberOfYearsUntilPaid(double numberOfYearsUntilPaid) {
        this.numberOfYearsUntilPaid = numberOfYearsUntilPaid;
    }
}
