package moller.solar.solarbackend.dto;

import java.time.LocalDate;

public class ReturnOnInvestmentEntryDto {

    private LocalDate date;
    private String description;
    private Boolean amountIsPositive;
    private Integer amountInMinorUnit;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAmountIsPositive() {
        return amountIsPositive;
    }

    public void setAmountIsPositive(Boolean amountIsPositive) {
        this.amountIsPositive = amountIsPositive;
    }

    public Integer getAmountInMinorUnit() {
        return amountInMinorUnit;
    }

    public void setAmountInMinorUnit(Integer amountInMinorUnit) {
        this.amountInMinorUnit = amountInMinorUnit;
    }
}
