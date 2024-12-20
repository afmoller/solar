package moller.solar.solarbackend.dto;

import java.time.LocalDate;

public class EnergySaleCompensationEntryDto {

    private Integer id;
    private LocalDate productionFromDate;
    private LocalDate productionToDate;
    private LocalDate compensationDate;
    private Integer productionYear;
    private Integer compensationAmountInMinorUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getProductionFromDate() {
        return productionFromDate;
    }

    public void setProductionFromDate(LocalDate productionFromDate) {
        this.productionFromDate = productionFromDate;
    }

    public Integer getCompensationAmountInMinorUnit() {
        return compensationAmountInMinorUnit;
    }

    public void setCompensationAmountInMinorUnit(Integer compensationAmountInMinorUnit) {
        this.compensationAmountInMinorUnit = compensationAmountInMinorUnit;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public LocalDate getProductionToDate() {
        return productionToDate;
    }

    public void setProductionToDate(LocalDate productionToDate) {
        this.productionToDate = productionToDate;
    }

    public LocalDate getCompensationDate() {
        return compensationDate;
    }

    public void setCompensationDate(LocalDate compensationDate) {
        this.compensationDate = compensationDate;
    }
}
