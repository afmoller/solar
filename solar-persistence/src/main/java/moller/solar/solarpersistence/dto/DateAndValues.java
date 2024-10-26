package moller.solar.solarpersistence.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateAndValues {

    private final List<LocalDate> date;

    public List<LocalDate> getDate() {
        return date;
    }

    public List<Integer> getProductionWattHours() {
        return productionWattHours;
    }

    public List<Integer> getSaleWattHours() {
        return saleWattHours;
    }

    public List<Integer> getSelfConsumptionWattHours() {
        return selfConsumptionWattHours;
    }

    public List<Integer> getPurchaseWattHours() {
        return purchaseWattHours;
    }

    public List<Integer> getConsumptionWattHours() {
        return consumptionWattHours;
    }

    private final List<Integer> productionWattHours;
    private final List<Integer> saleWattHours;
    private final List<Integer> selfConsumptionWattHours;
    private final List<Integer> purchaseWattHours;
    private final List<Integer> consumptionWattHours;

    private DateAndValues(DateAndValuesBuilder dateExportEntryBuilder) {
        this.date = dateExportEntryBuilder.date;
        this.saleWattHours = dateExportEntryBuilder.saleWattHours;
        this.purchaseWattHours = dateExportEntryBuilder.purchaseWattHours;
        this.productionWattHours = dateExportEntryBuilder.productionWattHours;
        this.consumptionWattHours = dateExportEntryBuilder.consumptionWattHours;
        this.selfConsumptionWattHours = dateExportEntryBuilder.selfConsumptionWattHours;
    }

    public static class DateAndValuesBuilder {

        private List<LocalDate> date = new ArrayList<>();
        private List<Integer> saleWattHours = new ArrayList<>();
        private List<Integer> purchaseWattHours = new ArrayList<>();
        private List<Integer> productionWattHours = new ArrayList<>();
        private List<Integer> consumptionWattHours = new ArrayList<>();
        private List<Integer> selfConsumptionWattHours = new ArrayList<>();

        public DateAndValuesBuilder setDate(List<LocalDate> date) {
            this.date = date;
            return this;
        }

        public DateAndValuesBuilder setProductionWattHours(List<Integer> productionWattHours) {
            this.productionWattHours = productionWattHours;
            return this;
        }

        public DateAndValuesBuilder setSaleWattHours(List<Integer> saleWattHours) {
            this.saleWattHours = saleWattHours;
            return this;
        }

        public DateAndValuesBuilder setSelfConsumptionWattHours(List<Integer> selfConsumptionWattHours) {
            this.selfConsumptionWattHours = selfConsumptionWattHours;
            return this;
        }

        public DateAndValuesBuilder setPurchaseWattHours(List<Integer> purchaseWattHours) {
            this.purchaseWattHours = purchaseWattHours;
            return this;
        }

        public DateAndValuesBuilder setConsumptionWattHours(List<Integer> consumptionWattHours) {
            this.consumptionWattHours = consumptionWattHours;
            return this;
        }

        public DateAndValues build() {
            return new DateAndValues(this);
        }
    }

    public static DateAndValues createDateAndValues(
            List<LocalDate> date,
            List<Integer> consumptionWattHours,
            List<Integer> productionWattHours,
            List<Integer> purchaseWattHours,
            List<Integer> selfConsumptionWattHours,
            List<Integer> saleWattHours) {

        return new DateAndValuesBuilder()
                .setDate(date)
                .setConsumptionWattHours(consumptionWattHours)
                .setProductionWattHours(productionWattHours)
                .setPurchaseWattHours(purchaseWattHours)
                .setSelfConsumptionWattHours(selfConsumptionWattHours)
                .setSaleWattHours(saleWattHours)
                .build();
    }
}
