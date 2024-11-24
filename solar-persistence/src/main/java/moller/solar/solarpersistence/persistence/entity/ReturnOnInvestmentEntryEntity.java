package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "RETURN_ON_INVESTMENT")
public class ReturnOnInvestmentEntryEntity {

    public static final String ID = "ID";
    public static final String DATE = "DATE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String AMOUNT_IS_POSITIVE = "AMOUNT_IS_POSITIVE";
    public static final String AMOUNT_IN_MINOR_UNIT = "AMOUNT_IN_MINOR_UNIT";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "return_on_investment_generator")
    @SequenceGenerator(name = "return_on_investment_generator", sequenceName = "RETURN_ON_INVESTMENT_SEQ", allocationSize = 1)
    @Column(name = ID)
    private Integer id;

    @Column(name = DATE)
    private LocalDate date;

    @Column(name = DESCRIPTION)
    private String description;

    @Column(name = AMOUNT_IS_POSITIVE)
    private Boolean amountIsPositive;

    @Column(name = AMOUNT_IN_MINOR_UNIT)
    private Integer amountInMinorUnit;

    /**
     * Needed for Hibernate
     */
    public ReturnOnInvestmentEntryEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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