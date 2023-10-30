package moller.solar.solarbackend.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateAndAccumulatedValues {

    private final List<LocalDate> date;

    public List<LocalDate> getDate() {
        return date;
    }

    public List<Integer> getAccumulatedProductionWattHours() {
        return accumulatedProductionWattHours;
    }

    public List<Integer> getAccumulatedSaleWattHours() {
        return accumulatedSaleWattHours;
    }

    public List<Integer> getAccumulatedSelfConsumptionWattHours() {
        return accumulatedSelfConsumptionWattHours;
    }

    public List<Integer> getAccumulatedPurchaseWattHours() {
        return accumulatedPurchaseWattHours;
    }

    public List<Integer> getAccumulatedConsumptionWattHours() {
        return accumulatedConsumptionWattHours;
    }

    private final List<Integer> accumulatedProductionWattHours;
    private final List<Integer> accumulatedSaleWattHours;
    private final List<Integer> accumulatedSelfConsumptionWattHours;
    private final List<Integer> accumulatedPurchaseWattHours;
    private final List<Integer> accumulatedConsumptionWattHours;

    private DateAndAccumulatedValues(DateAndAccumulatedValuesBuilder dateExportEntryBuilder) {
        this.date = dateExportEntryBuilder.date;
        this.accumulatedSaleWattHours = dateExportEntryBuilder.accumulatedSaleWattHours;
        this.accumulatedPurchaseWattHours = dateExportEntryBuilder.accumulatedPurchaseWattHours;
        this.accumulatedProductionWattHours = dateExportEntryBuilder.accumulatedProductionWattHours;
        this.accumulatedConsumptionWattHours = dateExportEntryBuilder.accumulatedConsumptionWattHours;
        this.accumulatedSelfConsumptionWattHours = dateExportEntryBuilder.accumulatedSelfConsumptionWattHours;
    }

    public static class DateAndAccumulatedValuesBuilder {

        private List<LocalDate> date = new ArrayList<>();
        private List<Integer> accumulatedSaleWattHours = new ArrayList<>();
        private List<Integer> accumulatedPurchaseWattHours = new ArrayList<>();
        private List<Integer> accumulatedProductionWattHours = new ArrayList<>();
        private List<Integer> accumulatedConsumptionWattHours = new ArrayList<>();
        private List<Integer> accumulatedSelfConsumptionWattHours = new ArrayList<>();

        public DateAndAccumulatedValuesBuilder setDate(List<LocalDate> date) {
            this.date = date;
            return this;
        }

        public DateAndAccumulatedValuesBuilder setAccumulatedProductionWattHours(List<Integer> accumulatedProductionWattHours) {
            this.accumulatedProductionWattHours = accumulatedProductionWattHours;
            return this;
        }

        public DateAndAccumulatedValuesBuilder setAccumulatedSaleWattHours(List<Integer> accumulatedSaleWattHours) {
            this.accumulatedSaleWattHours = accumulatedSaleWattHours;
            return this;
        }

        public DateAndAccumulatedValuesBuilder setAccumulatedSelfConsumptionWattHours(List<Integer> accumulatedSelfConsumptionWattHours) {
            this.accumulatedSelfConsumptionWattHours = accumulatedSelfConsumptionWattHours;
            return this;
        }

        public DateAndAccumulatedValuesBuilder setAccumulatedPurchaseWattHours(List<Integer> accumulatedPurchaseWattHours) {
            this.accumulatedPurchaseWattHours = accumulatedPurchaseWattHours;
            return this;
        }

        public DateAndAccumulatedValuesBuilder setAccumulatedConsumptionWattHours(List<Integer> accumulatedConsumptionWattHours) {
            this.accumulatedConsumptionWattHours = accumulatedConsumptionWattHours;
            return this;
        }

        public DateAndAccumulatedValues build() {
            return new DateAndAccumulatedValues(this);
        }
    }
}
