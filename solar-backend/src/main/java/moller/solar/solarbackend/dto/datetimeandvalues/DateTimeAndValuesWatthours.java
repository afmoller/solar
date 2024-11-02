package moller.solar.solarbackend.dto.datetimeandvalues;

import moller.solarpersistence.openapi.client.model.DataExportEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateTimeAndValuesWatthours extends DateTimeAndValues {

    protected DateTimeAndValuesWatthours(
            List<LocalDateTime> dateTimes,
            List<Integer> sale,
            List<Integer> purchase,
            List<Integer> production,
            List<Integer> consumption,
            List<Integer> selfConsumption) {

        super(dateTimes, sale, purchase, production, consumption, selfConsumption);
    }

    public List<Integer> getProductionWatthours() {
        return getProduction();
    }

    public List<Integer> getSaleWatthours() {
        return getSale();
    }

    public List<Integer> getSelfConsumptionWatthours() {
        return getSelfConsumption();
    }

    public List<Integer> getPurchaseWatthours() {
        return getPurchase();
    }

    public List<Integer> getConsumptionWatthours() {
        return getConsumption();
    }

    public static DateTimeAndValuesWatthours createDateTimeAndValues(List<DataExportEntry> dataExportEntries) {

        List<LocalDateTime> dateTimes = new ArrayList<>();
        List<Integer> saleWatthours = new ArrayList<>();
        List<Integer> purchaseWatthours = new ArrayList<>();
        List<Integer> productionWatthours = new ArrayList<>();
        List<Integer> consumptionWatthours = new ArrayList<>();
        List<Integer> selfConsumptionWatthours = new ArrayList<>();

        dataExportEntries.forEach(dataExportEntry -> {
            dateTimes.add(dataExportEntry.getTimestamp());
            saleWatthours.add(dataExportEntry.getDi3());
            purchaseWatthours.add(dataExportEntry.getDi4());
            productionWatthours.add(dataExportEntry.getDi1());
            consumptionWatthours.add(dataExportEntry.getDi2());
            selfConsumptionWatthours.add(dataExportEntry.getDi5());
        });

        return new DateTimeAndValuesWatthours(
                dateTimes,
                saleWatthours,
                purchaseWatthours,
                productionWatthours,
                consumptionWatthours,
                selfConsumptionWatthours);
    }
}
