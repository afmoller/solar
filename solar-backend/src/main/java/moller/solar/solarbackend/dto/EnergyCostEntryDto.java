package moller.solar.solarbackend.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class EnergyCostEntryDto {

    private Integer id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer feeOneInTenThousands;
    private Integer feeTwoInTenThousands;
    private Integer feeThreeInTenThousands;
    private Integer energyCostPerKwhInTenThousands;
    private Integer electricalGridCostInTenThousands;
    private Integer valueAddedTaxPercentageRateInMinorUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }


    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getFeeTwoInTenThousands() {
        return feeTwoInTenThousands;
    }

    public void setFeeTwoInTenThousands(Integer feeTwoInTenThousands) {
        this.feeTwoInTenThousands = feeTwoInTenThousands;
    }

    public Integer getFeeThreeInTenThousands() {
        return feeThreeInTenThousands;
    }

    public void setFeeThreeInTenThousands(Integer feeThreeInTenThousands) {
        this.feeThreeInTenThousands = feeThreeInTenThousands;
    }

    public Integer getFeeOneInTenThousands() {
        return feeOneInTenThousands;
    }

    public void setFeeOneInTenThousands(Integer feeOneInTenThousands) {
        this.feeOneInTenThousands = feeOneInTenThousands;
    }

    public Integer getEnergyCostPerKwhInTenThousands() {
        return energyCostPerKwhInTenThousands;
    }

    public void setEnergyCostPerKwhInTenThousands(Integer energyCostPerKwhInTenThousands) {
        this.energyCostPerKwhInTenThousands = energyCostPerKwhInTenThousands;
    }

    public Integer getElectricalGridCostInTenThousands() {
        return electricalGridCostInTenThousands;
    }

    public void setElectricalGridCostInTenThousands(Integer electricalGridCostInTenThousands) {
        this.electricalGridCostInTenThousands = electricalGridCostInTenThousands;
    }

    public Integer getValueAddedTaxPercentageRateInMinorUnit() {
        return valueAddedTaxPercentageRateInMinorUnit;
    }

    public void setValueAddedTaxPercentageRateInMinorUnit(Integer valueAddedTaxPercentageRateInMinorUnit) {
        this.valueAddedTaxPercentageRateInMinorUnit = valueAddedTaxPercentageRateInMinorUnit;
    }
}
