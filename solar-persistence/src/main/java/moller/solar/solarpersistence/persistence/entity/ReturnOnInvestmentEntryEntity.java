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

    private ReturnOnInvestmentEntryEntity(ReturnOnInvestmentEntryBuilder returnOnInvestmentEntryBuilder) {
        this.date = returnOnInvestmentEntryBuilder.date;
        this.description = returnOnInvestmentEntryBuilder.description;
        this.amountIsPositive = returnOnInvestmentEntryBuilder.amountIsPositive;
        this.amountInMinorUnit = returnOnInvestmentEntryBuilder.amountInMinorUnit;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getAmountIsPositive() {
        return amountIsPositive;
    }

    public Integer getAmountInMinorUnit() {
        return amountInMinorUnit;
    }

    public static class ReturnOnInvestmentEntryBuilder {
        private LocalDate date;
        private String description;
        private Boolean amountIsPositive;
        private Integer amountInMinorUnit;



        public ReturnOnInvestmentEntryBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public ReturnOnInvestmentEntryBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ReturnOnInvestmentEntryBuilder setAmountIsPositive(Boolean amountIsPositive) {
            this.amountIsPositive = amountIsPositive;
            return this;
        }

        public ReturnOnInvestmentEntryBuilder setAmountInMinorUnit(Integer amountInMinorUnit) {
            this.amountInMinorUnit = amountInMinorUnit;
            return this;
        }

        public ReturnOnInvestmentEntryEntity build() {
            return new ReturnOnInvestmentEntryEntity(this);
        }
    }
}
