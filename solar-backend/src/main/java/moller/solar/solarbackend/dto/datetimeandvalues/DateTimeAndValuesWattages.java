package moller.solar.solarbackend.dto.datetimeandvalues;

import moller.solar.solarbackend.persistence.DataExportEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateTimeAndValuesWattages extends DateTimeAndValues {

    public DateTimeAndValuesWattages(
            List<LocalDateTime> dateTimes,
            List<Integer> sale,
            List<Integer> purchase,
            List<Integer> production,
            List<Integer> consumption,
            List<Integer> selfConsumption) {

        super(dateTimes, sale, purchase, production, consumption, selfConsumption);
    }

    public List<Integer> getProductionWattages() {
        return getProduction();
    }

    public List<Integer> getSaleWattages() {
        return getSale();
    }

    public List<Integer> getSelfConsumptionWattages() {
        return getSelfConsumption();
    }

    public List<Integer> getPurchaseWattages() {
        return getPurchase();
    }

    public List<Integer> getConsumptionWattages() {
        return getConsumption();
    }

    public static DateTimeAndValuesWattages createDateTimeAndValues(List<DataExportEntry> dataExportEntries) {

        List<LocalDateTime> dateTimes = new ArrayList<>();
        List<Integer> saleWattages = new ArrayList<>();
        List<Integer> purchaseWattages = new ArrayList<>();
        List<Integer> productionWattages = new ArrayList<>();
        List<Integer> consumptionWattages = new ArrayList<>();
        List<Integer> selfConsumptionWattages = new ArrayList<>();

        dataExportEntries.forEach(dataExportEntry -> {
            dateTimes.add(dataExportEntry.getTimestamp());
            saleWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_3()));
            purchaseWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_4()));
            productionWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_1()));
            consumptionWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_2()));
            selfConsumptionWattages.add(convertImpulsesToWattage(dataExportEntry.getDi_5()));
        });

        return new DateTimeAndValuesWattages(
                dateTimes,
                saleWattages,
                purchaseWattages,
                productionWattages,
                consumptionWattages,
                selfConsumptionWattages);
    }

    private static Integer convertImpulsesToWattage(Integer pulses) {
        return pulses * 60;
    }
}
