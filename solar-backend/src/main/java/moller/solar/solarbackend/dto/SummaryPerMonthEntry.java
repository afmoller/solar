package moller.solar.solarbackend.dto;

public class SummaryPerMonthEntry {

    private Double autarchy;
    private Integer yearOfEntry;
    private Integer monthOfEntry;
    private Integer saleWattHours;
    private Integer purchaseWattHours;
    private Integer productionWattHours;
    private Integer consumptionWattHours;
    private Integer selfConsumptionWattHours;

    private SummaryPerMonthEntry(SummaryPerMonthEntryBuilder dateExportEntryBuilder) {
        this.autarchy = dateExportEntryBuilder.autarchy;
        this.yearOfEntry = dateExportEntryBuilder.yearOfEntry;
        this.monthOfEntry = dateExportEntryBuilder.monthOfEntry;
        this.saleWattHours = dateExportEntryBuilder.saleWattHours;
        this.purchaseWattHours = dateExportEntryBuilder.purchaseWattHours;
        this.productionWattHours = dateExportEntryBuilder.productionWattHours;
        this.consumptionWattHours = dateExportEntryBuilder.consumptionWattHours;
        this.selfConsumptionWattHours = dateExportEntryBuilder.selfConsumptionWattHours;
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

    public Integer getYearOfEntry() {
        return yearOfEntry;
    }

    public Integer getMonthOfEntry() {
        return monthOfEntry;
    }

    public static class SummaryPerMonthEntryBuilder {
        private Double autarchy;
        private Integer yearOfEntry;
        private Integer monthOfEntry;
        private Integer saleWattHours;
        private Integer purchaseWattHours;
        private Integer productionWattHours;
        private Integer consumptionWattHours;
        private Integer selfConsumptionWattHours;

        public SummaryPerMonthEntryBuilder setProductionWattHours(Integer productionWattHours) {
            this.productionWattHours = productionWattHours;
            return this;
        }

        public SummaryPerMonthEntryBuilder setSaleWattHours(Integer saleWattHours) {
            this.saleWattHours = saleWattHours;
            return this;
        }

        public SummaryPerMonthEntryBuilder setSelfConsumptionWattHours(Integer selfConsumptionWattHours) {
            this.selfConsumptionWattHours = selfConsumptionWattHours;
            return this;
        }

        public SummaryPerMonthEntryBuilder setPurchaseWattHours(Integer purchaseWattHours) {
            this.purchaseWattHours = purchaseWattHours;
            return this;
        }

        public SummaryPerMonthEntryBuilder setConsumptionWattHours(Integer consumptionWattHours) {
            this.consumptionWattHours = consumptionWattHours;
            return this;
        }

        public SummaryPerMonthEntryBuilder setAutarchy(Double autarchy) {
            this.autarchy = autarchy;
            return this;
        }

        public SummaryPerMonthEntryBuilder setYearOfEntry(Integer yearOfEntry) {
            this.yearOfEntry = yearOfEntry;
            return this;
        }

        public SummaryPerMonthEntryBuilder setMonthOfEntry(Integer monthOfEntry) {
            this.monthOfEntry = monthOfEntry;
            return this;
        }

        public SummaryPerMonthEntry build() {
            return new SummaryPerMonthEntry(this);
        }
    }
}
