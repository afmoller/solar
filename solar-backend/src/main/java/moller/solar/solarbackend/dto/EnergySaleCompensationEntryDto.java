package moller.solar.solarbackend.dto;

import java.time.LocalDate;

public class EnergySaleCompensationEntryDto {

    private Integer id;
    private LocalDate productionFrom;
    private LocalDate productionTo;
    private LocalDate compensationDate;
    private Integer productionYear;
    private Integer compensationInMinorUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getProductionFrom() {
        return productionFrom;
    }

    public void setProductionFrom(LocalDate productionFrom) {
        this.productionFrom = productionFrom;
    }

    public Integer getCompensationInMinorUnit() {
        return compensationInMinorUnit;
    }

    public void setCompensationInMinorUnit(Integer compensationInMinorUnit) {
        this.compensationInMinorUnit = compensationInMinorUnit;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public LocalDate getProductionTo() {
        return productionTo;
    }

    public void setProductionTo(LocalDate productionTo) {
        this.productionTo = productionTo;
    }

    public LocalDate getCompensationDate() {
        return compensationDate;
    }

    public void setCompensationDate(LocalDate compensationDate) {
        this.compensationDate = compensationDate;
    }
}
