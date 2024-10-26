package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ENERGY_COST")
public class EnergyCostEntryEntity {

    public static final String ID = "ID";
    public static final String FROM_DATE = "FROM_DATE";
    public static final String TO_DATE = "TO_DATE";
    public static final String FEE_ONE_IN_TEN_THOUSANDS = "FEE_ONE_IN_TEN_THOUSANDS";
    public static final String FEE_TWO_IN_TEN_THOUSANDS = "FEE_TWO_IN_TEN_THOUSANDS";
    public static final String FEE_THREE_IN_TEN_THOUSANDS = "FEE_THREE_IN_TEN_THOUSANDS";
    public static final String ENERGY_COST_PER_KWH_IN_TEN_THOUSANDS = "ENERGY_COST_PER_KWH_IN_TEN_THOUSANDS";
    public static final String ELECTRICAL_GRID_COST_IN_TEN_THOUSANDS = "ELECTRICAL_GRID_COST_IN_TEN_THOUSANDS";
    public static final String VALUE_ADDED_TAX_PERCENTAGE_RATE_IN_MINOR_UNIT = "VALUE_ADDED_TAX_PERCENTAGE_RATE_IN_MINOR_UNIT";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "energy_cost_generator")
    @SequenceGenerator(name = "energy_cost_generator", sequenceName = "ENERGY_COST_SEQ", allocationSize = 1)
    @Column(name = ID)
    private Integer id;

    @Column(name = FROM_DATE)
    private LocalDate fromDate;

    @Column(name = TO_DATE)
    private LocalDate toDate;

    @Column(name = FEE_ONE_IN_TEN_THOUSANDS)
    private Integer feeOneInTenThousands;

    @Column(name = FEE_TWO_IN_TEN_THOUSANDS)
    private Integer feeTwoInTenThousands;

    @Column(name = FEE_THREE_IN_TEN_THOUSANDS)
    private Integer feeThreeInTenThousands;

    @Column(name = ENERGY_COST_PER_KWH_IN_TEN_THOUSANDS)
    private Integer energyCostPerKwhInTenThousands;

    @Column(name = ELECTRICAL_GRID_COST_IN_TEN_THOUSANDS)
    private Integer electricalGridCostInTenThousands;

    @Column(name = VALUE_ADDED_TAX_PERCENTAGE_RATE_IN_MINOR_UNIT)
    private Integer valueAddedTaxPercentageRateInMinorUnit;

    /**
     * Needed for Hibernate
     */
    public EnergyCostEntryEntity() {
    }

    private EnergyCostEntryEntity(EnergyCostEntryBuilder energyCostEntryBuilder) {
        this.fromDate = energyCostEntryBuilder.fromDate;
        this.toDate = energyCostEntryBuilder.toDate;
        this.feeOneInTenThousands = energyCostEntryBuilder.feeOneInTenThousands;
        this.feeTwoInTenThousands = energyCostEntryBuilder.feeTwoInTenThousands;
        this.feeThreeInTenThousands = energyCostEntryBuilder.feeThreeInTenThousands;
        this.energyCostPerKwhInTenThousands = energyCostEntryBuilder.energyCostPerKwhInTenThousands;
        this.electricalGridCostInTenThousands = energyCostEntryBuilder.electricalGridCostInTenThousands;
        this.valueAddedTaxPercentageRateInMinorUnit = energyCostEntryBuilder.valueAddedTaxPercentageRateInMinorUnit;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Integer getFeeOneInTenThousands() {
        return feeOneInTenThousands;
    }

    public Integer getFeeTwoInTenThousands() {
        return feeTwoInTenThousands;
    }

    public Integer getFeeThreeInTenThousands() {
        return feeThreeInTenThousands;
    }

    public Integer getEnergyCostPerKwhInTenThousands() {
        return energyCostPerKwhInTenThousands;
    }

    public Integer getElectricalGridCostInTenThousands() {
        return electricalGridCostInTenThousands;
    }

    public Integer getValueAddedTaxPercentageRateInMinorUnit() {
        return valueAddedTaxPercentageRateInMinorUnit;
    }

    public static class EnergyCostEntryBuilder {
        private LocalDate fromDate;
        private LocalDate toDate;
        private Integer feeOneInTenThousands;
        private Integer feeTwoInTenThousands;
        private Integer feeThreeInTenThousands;
        private Integer energyCostPerKwhInTenThousands;
        private Integer electricalGridCostInTenThousands;
        private Integer valueAddedTaxPercentageRateInMinorUnit;

        public EnergyCostEntryBuilder setFromDate(LocalDate fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public EnergyCostEntryBuilder setToDate(LocalDate toDate) {
            this.toDate = toDate;
            return this;
        }

        public EnergyCostEntryBuilder setFeeOneInTenThousands(Integer feeOneInTenThousands) {
            this.feeOneInTenThousands = feeOneInTenThousands;
            return this;
        }

        public EnergyCostEntryBuilder setFeeTwoInTenThousands(Integer feeTwoInTenThousands) {
            this.feeTwoInTenThousands = feeTwoInTenThousands;
            return this;
        }

        public EnergyCostEntryBuilder setFeeThreeInTenThousands(Integer feeThreeInTenThousands) {
            this.feeThreeInTenThousands = feeThreeInTenThousands;
            return this;
        }

        public EnergyCostEntryBuilder setEnergyCostPerKwhInTenThousands(Integer energyCostPerKwhInTenThousands) {
            this.energyCostPerKwhInTenThousands = energyCostPerKwhInTenThousands;
            return this;
        }

        public EnergyCostEntryBuilder setElectricalGridCostInTenThousands(Integer electricalGridCostInTenThousands) {
            this.electricalGridCostInTenThousands = electricalGridCostInTenThousands;
            return this;
        }

        public EnergyCostEntryBuilder setValueAddedTaxPercentageRateInMinorUnit(Integer valueAddedTaxPercentageRateInMinorUnit) {
            this.valueAddedTaxPercentageRateInMinorUnit = valueAddedTaxPercentageRateInMinorUnit;
            return this;
        }
        public EnergyCostEntryEntity build() {
            return new EnergyCostEntryEntity(this);
        }
    }
}
