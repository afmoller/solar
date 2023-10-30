package moller.solar.solarbackend.summary;

import moller.solar.solarbackend.persistence.DataExportEntry;
import moller.solar.solarbackend.persistence.SummaryPerDayEntry;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SummaryEntryValues extends HashMap<String, AtomicInteger> {

    private static final String KEY_ACCUMULATED = "ACCUMULATED_";
    private static final String KEY_SALE_WATT_HOURS = "SALE_WATT_HOURS";
    private static final String KEY_PURCHASE_WATT_HOURS = "PURCHASE_WATT_HOURS";
    private static final String KEY_PRODUCTION_WATT_HOURS = "PRODUCTION_WATT_HOURS";
    private static final String KEY_CONSUMPTION_WATT_HOURS = "CONSUMPTION_WATT_HOURS";
    private static final String KEY_SELF_CONSUMPTION_WATT_HOURS = "SELF_CONSUMPTION_WATT_HOURS";

    public SummaryEntryValues() {
        initializeKeyValue(KEY_SALE_WATT_HOURS);
        initializeKeyValue(KEY_PURCHASE_WATT_HOURS);
        initializeKeyValue(KEY_PRODUCTION_WATT_HOURS);
        initializeKeyValue(KEY_CONSUMPTION_WATT_HOURS);
        initializeKeyValue(KEY_SELF_CONSUMPTION_WATT_HOURS);

        initializeKeyValue(KEY_ACCUMULATED, KEY_SALE_WATT_HOURS);
        initializeKeyValue(KEY_ACCUMULATED, KEY_PURCHASE_WATT_HOURS);
        initializeKeyValue(KEY_ACCUMULATED, KEY_PRODUCTION_WATT_HOURS);
        initializeKeyValue(KEY_ACCUMULATED, KEY_CONSUMPTION_WATT_HOURS);
        initializeKeyValue(KEY_ACCUMULATED, KEY_SELF_CONSUMPTION_WATT_HOURS);
    }

    public void addNonCumulatedValues(DataExportEntry dataExportEntry) {
        this.get(KEY_SALE_WATT_HOURS).addAndGet(dataExportEntry.getDi_3());
        this.get(KEY_PURCHASE_WATT_HOURS).addAndGet(dataExportEntry.getDi_4());
        this.get(KEY_PRODUCTION_WATT_HOURS).addAndGet(dataExportEntry.getDi_1());
        this.get(KEY_CONSUMPTION_WATT_HOURS).addAndGet(dataExportEntry.getDi_2());
        this.get(KEY_SELF_CONSUMPTION_WATT_HOURS).addAndGet(dataExportEntry.getDi_5());
    }

    public int getSaleWattHours() {
        return get(KEY_SALE_WATT_HOURS).get();
    }

    public int getPurchaseWattHours() {
        return get(KEY_PURCHASE_WATT_HOURS).get();
    }

    public int getProductionWattHours() {
        return get(KEY_PRODUCTION_WATT_HOURS).get();
    }

    public int getConsumptionWattHours() {
        return get(KEY_CONSUMPTION_WATT_HOURS).get();
    }

    public int getSelfConsumptionWattHours() {
        return get(KEY_SELF_CONSUMPTION_WATT_HOURS).get();
    }

    public int getAccumulatedSaleWattHours() {
        return getAccumulatedValue(KEY_SALE_WATT_HOURS);
    }

    public int getAccumulatedPurchaseWattHours() {
        return getAccumulatedValue(KEY_PURCHASE_WATT_HOURS);
    }

    public int getAccumulatedProductionWattHours() {
        return getAccumulatedValue(KEY_PRODUCTION_WATT_HOURS);
    }

    public int getAccumulatedConsumptionWattHours() {
        return getAccumulatedValue(KEY_CONSUMPTION_WATT_HOURS);
    }

    public int getAccumulatedSelfConsumptionWattHours() {
        return getAccumulatedValue(KEY_SELF_CONSUMPTION_WATT_HOURS);
    }

    public int setAccumulatedSaleWattHours(AtomicInteger delta) {
        return setAccumulatedValueForKey(KEY_SALE_WATT_HOURS, delta);
    }

    public int setAccumulatedPurchaseWattHours(AtomicInteger delta) {
        return setAccumulatedValueForKey(KEY_PURCHASE_WATT_HOURS, delta);
    }

    public int setAccumulatedProductionWattHours(AtomicInteger delta) {
        return setAccumulatedValueForKey(KEY_PRODUCTION_WATT_HOURS, delta);
    }

    public int setAccumulatedConsumptionWattHours(AtomicInteger delta) {
        return setAccumulatedValueForKey(KEY_CONSUMPTION_WATT_HOURS, delta);
    }

    public int setAccumulatedSelfConsumptionWattHours(AtomicInteger delta) {
        return setAccumulatedValueForKey(KEY_SELF_CONSUMPTION_WATT_HOURS, delta);
    }

    public SummaryPerDayEntry mapToSummaryPerDayEntry(LocalDate date) {
        return new SummaryPerDayEntry.SummaryPerDayEntryBuilder()
                .setDate(date)
                .setAutarchy((((double) getSelfConsumptionWattHours()) / ((double) getConsumptionWattHours())) * 100)
                .setYearOfEntry(date.getYear())
                .setMonthOfEntry(date.getMonthValue())
                .setSaleWattHours(getSaleWattHours())
                .setPurchaseWattHours(getPurchaseWattHours())
                .setProductionWattHours(getProductionWattHours())
                .setConsumptionWattHours(getConsumptionWattHours())
                .setSelfConsumptionWattHours(getSelfConsumptionWattHours())
                .setAccumulatedSaleWattHours(getAccumulatedSaleWattHours())
                .setAccumulatedPurchaseWattHours(getAccumulatedPurchaseWattHours())
                .setAccumulatedProductionWattHours(getAccumulatedProductionWattHours())
                .setAccumulatedConsumptionWattHours(getAccumulatedConsumptionWattHours())
                .setAccumulatedSelfConsumptionWattHours(getAccumulatedSelfConsumptionWattHours())
                .build();
    }

    private void initializeKeyValue(String key) {
        initializeKeyValue("", key);
    }

    private void initializeKeyValue(String keyPrefix, String key) {
        this.put(keyPrefix + key, new AtomicInteger());
    }

    private int getAccumulatedValue(String key) {
        return this.get(KEY_ACCUMULATED + key).get();
    }

    private void setAccumulatedValue(String key, int value) {
        this.get(KEY_ACCUMULATED + key).set(value);
    }

    private int setAccumulatedValueForKey(String key, AtomicInteger delta) {
        AtomicInteger value = this.get(key);

        AtomicInteger accumulatedValue = new AtomicInteger(delta.get());
        accumulatedValue.addAndGet(value.get());

        setAccumulatedValue(key, accumulatedValue.get());

        return accumulatedValue.get();
    }
}
