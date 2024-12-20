package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ENERGY_SALE_COMPENSATION")
public class EnergySaleCompensationEntryEntity {

    public static final String ID = "ID";
    public static final String COMPENSATION_DATE = "COMPENSATION_DATE";
    public static final String PRODUCTION_FROM_DATE = "PRODUCTION_FROM_DATE";
    public static final String PRODUCTION_TO_DATE = "PRODUCTION_TO_DATE";
    public static final String PRODUCTION_YEAR = "PRODUCTION_YEAR";
    public static final String COMPENSATION_AMOUNT_IN_MINOR_UNIT = "COMPENSATION_AMOUNT_IN_MINOR_UNIT";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "energy_sale_compensation_generator")
    @SequenceGenerator(name = "energy_sale_compensation_generator", sequenceName = "ENERGY_SALE_COMPENSATION_SEQ", allocationSize = 1)
    @Column(name = ID)
    private Integer id;

    @Column(name = COMPENSATION_DATE)
    private LocalDate compensationDate;

    @Column(name = PRODUCTION_FROM_DATE)
    private LocalDate productionFromDate;

    @Column(name = PRODUCTION_TO_DATE)
    private LocalDate productionToDate;

    @Column(name = PRODUCTION_YEAR)
    private Integer productionYear;

    @Column(name = COMPENSATION_AMOUNT_IN_MINOR_UNIT)
    private Integer compensationAmountInMinorUnit;

    /**
     * Needed for Hibernate
     */
    public EnergySaleCompensationEntryEntity() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public LocalDate getCompensationDate() {
        return compensationDate;
    }

    public LocalDate getProductionFromDate() {
        return productionFromDate;
    }

    public LocalDate getProductionToDate() {
        return productionToDate;
    }

    public Integer getCompensationAmountInMinorUnit() {
        return compensationAmountInMinorUnit;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCompensationDate(LocalDate compensationDate) {
        this.compensationDate = compensationDate;
    }

    public void setProductionFromDate(LocalDate productionFromDate) {
        this.productionFromDate = productionFromDate;
    }

    public void setProductionToDate(LocalDate productionToDate) {
        this.productionToDate = productionToDate;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public void setCompensationAmountInMinorUnit(Integer compensationAmountInMinorUnit) {
        this.compensationAmountInMinorUnit = compensationAmountInMinorUnit;
    }
}