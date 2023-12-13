package moller.solar.solarbackend.dto;

import moller.solar.solarbackend.persistence.DataExportEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateTimeAndValues {

    private final List<LocalDateTime> dateTimes;
    private final List<Integer> saleWattages;
    private final List<Integer> purchaseWattages;
    private final List<Integer> productionWattages;
    private final List<Integer> consumptionWattages;
    private final List<Integer> selfConsumptionWattages;

    public List<LocalDateTime> getDateTimes() {
        return dateTimes;
    }

    public List<Integer> getProductionWattages() {
        return productionWattages;
    }

    public List<Integer> getSaleWattages() {
        return saleWattages;
    }

    public List<Integer> getSelfConsumptionWattages() {
        return selfConsumptionWattages;
    }

    public List<Integer> getPurchaseWattages() {
        return purchaseWattages;
    }

    public List<Integer> getConsumptionWattages() {
        return consumptionWattages;
    }

    private DateTimeAndValues(DateTimeAndValuesBuilder dateExportEntryBuilder) {
        this.dateTimes = dateExportEntryBuilder.dateTimes;
        this.saleWattages = dateExportEntryBuilder.saleWattages;
        this.purchaseWattages = dateExportEntryBuilder.purchaseWattages;
        this.productionWattages = dateExportEntryBuilder.productionWattages;
        this.consumptionWattages = dateExportEntryBuilder.consumptionWattages;
        this.selfConsumptionWattages = dateExportEntryBuilder.selfConsumptionWattages;
    }

    public static class DateTimeAndValuesBuilder {

        private List<LocalDateTime> dateTimes = new ArrayList<>();
        private List<Integer> saleWattages = new ArrayList<>();
        private List<Integer> purchaseWattages = new ArrayList<>();
        private List<Integer> productionWattages = new ArrayList<>();
        private List<Integer> consumptionWattages = new ArrayList<>();
        private List<Integer> selfConsumptionWattages = new ArrayList<>();

        public DateTimeAndValuesBuilder setDates(List<LocalDateTime> dateTimes) {
            this.dateTimes = dateTimes;
            return this;
        }

        public DateTimeAndValuesBuilder setProductionWattages(List<Integer> productionWattages) {
            this.productionWattages = productionWattages;
            return this;
        }

        public DateTimeAndValuesBuilder setSaleWattages(List<Integer> saleWattages) {
            this.saleWattages = saleWattages;
            return this;
        }

        public DateTimeAndValuesBuilder setSelfConsumptionWattages(List<Integer> selfConsumptionWattages) {
            this.selfConsumptionWattages = selfConsumptionWattages;
            return this;
        }

        public DateTimeAndValuesBuilder setPurchaseWattages(List<Integer> purchaseWattages) {
            this.purchaseWattages = purchaseWattages;
            return this;
        }

        public DateTimeAndValuesBuilder setConsumptionWattages(List<Integer> consumptionWattages) {
            this.consumptionWattages = consumptionWattages;
            return this;
        }

        public DateTimeAndValues build() {
            return new DateTimeAndValues(this);
        }
    }

    public static DateTimeAndValues createDateTimeAndValues(List<DataExportEntry> dataExportEntries) {

        List<LocalDateTime> dateTimes = new ArrayList<>();
        List<Integer> consumptionWattages = new ArrayList<>();
        List<Integer> productionWattages = new ArrayList<>();
        List<Integer> purchaseWattages = new ArrayList<>();
        List<Integer> selfConsumptionWattages = new ArrayList<>();
        List<Integer> saleWattages = new ArrayList<>();

        dataExportEntries.forEach(dataExportEntry -> {
            dateTimes.add(dataExportEntry.getTimestamp());
            saleWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_3()));
            purchaseWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_4()));
            productionWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_1()));
            consumptionWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_2()));
            selfConsumptionWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_5()));
        });

        return new DateTimeAndValuesBuilder()
                .setDates(dateTimes)
                .setConsumptionWattages(consumptionWattages)
                .setProductionWattages(productionWattages)
                .setPurchaseWattages(purchaseWattages)
                .setSelfConsumptionWattages(selfConsumptionWattages)
                .setSaleWattages(saleWattages)
                .build();
    }

    private static Integer convertImpulsesToWattage(Integer pulses) {
        return pulses * 60;
    }
}
