package moller.solar.solarpersistence.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "SUMMARY_PER_DAY")
public class SummaryPerDayEntryEntity {

    public static final String DATE = "DATE";
    public static final String AUTARCHY = "AUTARCHY";
    public static final String YEAR_OF_ENTRY = "YEAR_OF_ENTRY";
    public static final String MONTH_OF_ENTRY = "MONTH_OF_ENTRY";

    public static final String SALE_WATT_HOURS = "SALE_WATT_HOURS";
    public static final String PURCHASE_WATT_HOURS = "PURCHASE_WATT_HOURS";
    public static final String PRODUCTION_WATT_HOURS = "PRODUCTION_WATT_HOURS";
    public static final String CONSUMPTION_WATT_HOURS = "CONSUMPTION_WATT_HOURS";
    public static final String SELF_CONSUMPTION_WATT_HOURS = "SELF_CONSUMPTION_WATT_HOURS";

    public static final String ACCUMULATED_SALE_WATT_HOURS = "ACCUMULATED_SALE_WATT_HOURS";
    public static final String ACCUMULATED_PURCHASE_WATT_HOURS = "ACCUMULATED_PURCHASE_WATT_HOURS";
    public static final String ACCUMULATED_PRODUCTION_WATT_HOURS = "ACCUMULATED_PRODUCTION_WATT_HOURS";
    public static final String ACCUMULATED_CONSUMPTION_WATT_HOURS = "ACCUMULATED_CONSUMPTION_WATT_HOURS";
    public static final String ACCUMULATED_SELF_CONSUMPTION_WATT_HOURS = "ACCUMULATED_SELF_CONSUMPTION_WATT_HOURS";

    @Id
    @Column(name = DATE)
    private LocalDate date;

    @Column(name = AUTARCHY)
    private Double autarchy;

    @Column(name = YEAR_OF_ENTRY)
    private Integer yearOfEntry;

    @Column(name = MONTH_OF_ENTRY)
    private Integer monthOfEntry;

    @Column(name = SALE_WATT_HOURS)
    private Integer saleWattHours;

    @Column(name = PURCHASE_WATT_HOURS)
    private Integer purchaseWattHours;

    @Column(name = PRODUCTION_WATT_HOURS)
    private Integer productionWattHours;

    @Column(name = CONSUMPTION_WATT_HOURS)
    private Integer consumptionWattHours;

    @Column(name = SELF_CONSUMPTION_WATT_HOURS)
    private Integer selfConsumptionWattHours;

    @Column(name = ACCUMULATED_SALE_WATT_HOURS)
    private Integer accumulatedSaleWattHours;

    @Column(name = ACCUMULATED_PURCHASE_WATT_HOURS)
    private Integer accumulatedPurchaseWattHours;

    @Column(name = ACCUMULATED_PRODUCTION_WATT_HOURS)
    private Integer accumulatedProductionWattHours;

    @Column(name = ACCUMULATED_CONSUMPTION_WATT_HOURS)
    private Integer accumulatedConsumptionWattHours;

    @Column(name = ACCUMULATED_SELF_CONSUMPTION_WATT_HOURS)
    private Integer accumulatedSelfConsumptionWattHours;

    /**
     * Needed for Hibernate
     */
    public SummaryPerDayEntryEntity() {
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getProductionWattHours() {
        return productionWattHours;
    }

    public Integer getSaleWattHours() {
        return saleWattHours;
    }

    public Integer getSelfConsumptionWattHours() {
        return selfConsumptionWattHours;
    }

    public Integer getPurchaseWattHours() {
        return purchaseWattHours;
    }

    public Integer getConsumptionWattHours() {
        return consumptionWattHours;
    }

    public Double getAutarchy() {
        return autarchy;
    }

    public Integer getAccumulatedProductionWattHours() {
        return accumulatedProductionWattHours;
    }

    public Integer getAccumulatedSaleWattHours() {
        return accumulatedSaleWattHours;
    }

    public Integer getAccumulatedSelfConsumptionWattHours() {
        return accumulatedSelfConsumptionWattHours;
    }

    public Integer getAccumulatedPurchaseWattHours() {
        return accumulatedPurchaseWattHours;
    }

    public Integer getAccumulatedConsumptionWattHours() {
        return accumulatedConsumptionWattHours;
    }

    public Integer getYearOfEntry() {
        return yearOfEntry;
    }

    public Integer getMonthOfEntry() {
        return monthOfEntry;
    }
}