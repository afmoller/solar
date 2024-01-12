package moller.solar.solarbackend.dto.datetimeandvalues;

import java.time.LocalDateTime;
import java.util.List;

public abstract class DateTimeAndValues {

    private final List<LocalDateTime> dateTimes;
    private final List<Integer> sale;
    private final List<Integer> purchase;
    private final List<Integer> production;
    private final List<Integer> consumption;
    private final List<Integer> selfConsumption;

    public List<LocalDateTime> getDateTimes() {
        return dateTimes;
    }

    protected List<Integer> getSale() {
        return sale;
    }

    protected List<Integer> getPurchase() {
        return purchase;
    }

    protected List<Integer> getProduction() {
        return production;
    }

    protected List<Integer> getConsumption() {
        return consumption;
    }

    protected List<Integer> getSelfConsumption() {
        return selfConsumption;
    }

    protected DateTimeAndValues(
            List<LocalDateTime> dateTimes,
            List<Integer> sale,
            List<Integer> purchase,
            List<Integer> production,
            List<Integer> consumption,
            List<Integer> selfConsumption) {

        this.dateTimes = dateTimes;
        this.sale = sale;
        this.purchase = purchase;
        this.production = production;
        this.consumption = consumption;
        this.selfConsumption = selfConsumption;
    }
}
