package moller.solar.solarbackend.dto;

import java.util.List;

public class YearAndMonthProductionValues {
    private List<Integer> years;
    private List<Integer> monthValues;

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setMonthValues(List<Integer> monthValues) {
        this.monthValues = monthValues;
    }

    public List<Integer> getMonthValues() {
        return monthValues;
    }
}
