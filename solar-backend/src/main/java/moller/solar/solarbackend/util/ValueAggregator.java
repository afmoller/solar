package moller.solar.solarbackend.util;

import moller.solar.solarbackend.persistence.SummaryPerDayEntry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

@Component
public class ValueAggregator {

    public Map<Integer, Map<Integer, Integer>> aggregateOnMonth(List<SummaryPerDayEntry> allEntries, Function<SummaryPerDayEntry, Integer> function) {
        Map<Integer, Map<Integer, Integer>> yearToMonthAndValue = new TreeMap<>();

        allEntries.forEach(summaryPerDayEntry -> {
            Integer yearOfEntry = summaryPerDayEntry.getYearOfEntry();
            Integer monthOfEntry = summaryPerDayEntry.getMonthOfEntry();
            Integer value = getIntegerValue(summaryPerDayEntry, function);

            Map<Integer, Integer> monthToValue = yearToMonthAndValue.get(yearOfEntry);
            if (monthToValue == null) {
                monthToValue = new TreeMap<>();
                initiateMonthToValueMap(monthToValue);
                yearToMonthAndValue.put(yearOfEntry, monthToValue);
            }

            monthToValue.put(monthOfEntry, monthToValue.get(monthOfEntry) + value);
        });

        return yearToMonthAndValue;
    }

    public Integer getIntegerValue(SummaryPerDayEntry summaryPerDayEntry, Function<SummaryPerDayEntry, Integer> function) {
        return function.apply(summaryPerDayEntry);
    }

    private void initiateMonthToValueMap(Map<Integer, Integer> mapToInitiate) {
        for (int i = 1; i <= 12; i++) {
            mapToInitiate.put(i, 0);
        }
    }
}
