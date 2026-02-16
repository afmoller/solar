package moller.solar.solarbackend.dto.timeandvalues;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeAndWeatherValues {

    private final List<LocalTime> minToMaxLocalTimes;

    private final Integer[] uvValues;
    private final Integer[] humidityIndoorValues;
    private final Integer[] humidityOutdoorValues;
    private final Float[] solarRadiationValues;
    private final Float[] baromAbsoluteIndoorValues;
    private final Float[] dailyRainInMillimetersValues;
    private final Float[] hourlyRainInMillimetersValues;
    private final Float[] windSpeedInMeterPerSecondValues;
    private final Float[] temperatureIndoorInCelsiusValues;
    private final Float[] temperatureOutdoorInCelsiusValues;
    private final Float[] maxDailyGustInMeterPerSecondValues;
    private final Float[] rainRateInMillimetersPerHourValues;

    public TimeAndWeatherValues(LocalTime queryEndTime) {
        minToMaxLocalTimes = initializeMinToMaxLocalTimesList(queryEndTime);

        int size = minToMaxLocalTimes.size();

        uvValues = new Integer[size];
        humidityIndoorValues = new Integer[size];
        humidityOutdoorValues = new Integer[size];
        solarRadiationValues = new Float[size];
        baromAbsoluteIndoorValues = new Float[size];
        dailyRainInMillimetersValues = new Float[size];
        hourlyRainInMillimetersValues = new Float[size];
        windSpeedInMeterPerSecondValues = new Float[size];
        temperatureIndoorInCelsiusValues = new Float[size];
        temperatureOutdoorInCelsiusValues = new Float[size];
        maxDailyGustInMeterPerSecondValues = new Float[size];
        rainRateInMillimetersPerHourValues = new Float[size];
    }

    public void setValueToUvList(int index, int uv) {
        uvValues[index] = uv;
    }

    public void setValueToHumidityIndoorList(int index, int humidityIndoor) {
        humidityIndoorValues[index] = humidityIndoor;
    }

    public void setValueToHumidityOutdoorList(int index, int humidityOutdoor) {
        humidityOutdoorValues[index] = humidityOutdoor;
    }

    public void setValueToSolarRadiationList(int index, Float solarRadiation) {
        solarRadiationValues[index] = solarRadiation;
    }

    public void setValueToBaromAbsoluteIndoorList(int index, Float baromAbsoluteIndoor) {
        baromAbsoluteIndoorValues[index] = baromAbsoluteIndoor;
    }

    public void setValueToDailyRainInMillimetersList(int index, Float dailyRainInMillimeter) {
        dailyRainInMillimetersValues[index] = dailyRainInMillimeter;
    }

    public void setValueToHourlyRainInMillimetersList(int index, Float hourlyRainInMillimeter) {
        hourlyRainInMillimetersValues[index] = hourlyRainInMillimeter;
    }

    public void setValueToWindSpeedInMeterPerSecondsList(int index, Float windSpeedInMeterPerSecond) {
        windSpeedInMeterPerSecondValues[index] = windSpeedInMeterPerSecond;
    }

    public void setValueToTemperatureIndoorInCelsiusList(int index, Float temperatureIndoorInCelcius) {
        temperatureIndoorInCelsiusValues[index] = temperatureIndoorInCelcius;
    }

    public void setValueToTemperatureOutdoorInCelsiusList(int index, Float temperatureOutdoorInCelcius) {
        temperatureOutdoorInCelsiusValues[index] = temperatureOutdoorInCelcius;
    }

    public void setValueToMaxDailyGustInMeterPerSecondList(int index, Float maxDailyGustInMeterPerSecond) {
        maxDailyGustInMeterPerSecondValues[index] = maxDailyGustInMeterPerSecond;
    }

    public void setValueToRainRateInMillimetersPerHourList(int index, Float rainRateInMillimetersPerHour) {
        rainRateInMillimetersPerHourValues[index] = rainRateInMillimetersPerHour;
    }

    public List<LocalTime> getMinToMaxLocalTimes() {
        return minToMaxLocalTimes;
    }

    public Integer[] getUvValues() {
        return uvValues;
    }

    public Integer[] getHumidityIndoorValues() {
        return humidityIndoorValues;
    }

    public Integer[] getHumidityOutdoorValues() {
        return humidityOutdoorValues;
    }

    public Float[] getSolarRadiationValues() {
        return solarRadiationValues;
    }

    public Float[] getBaromAbsoluteIndoorValues() {
        return baromAbsoluteIndoorValues;
    }

    public Float[] getDailyRainInMillimetersValues() {
        return dailyRainInMillimetersValues;
    }

    public Float[] getHourlyRainInMillimetersValues() {
        return hourlyRainInMillimetersValues;
    }

    public Float[] getWindSpeedInMeterPerSecondValues() {
        return windSpeedInMeterPerSecondValues;
    }

    public Float[] getTemperatureIndoorInCelsiusValues() {
        return temperatureIndoorInCelsiusValues;
    }

    public Float[] getTemperatureOutdoorInCelsiusValues() {
        return temperatureOutdoorInCelsiusValues;
    }

    public Float[] getMaxDailyGustInMeterPerSecondValues() {
        return maxDailyGustInMeterPerSecondValues;
    }

    public Float[] getRainRateInMillimetersPerHourValues() {
        return rainRateInMillimetersPerHourValues;
    }

    private List<LocalTime> initializeMinToMaxLocalTimesList(LocalTime queryEndTime) {

        LocalTime queryLocalTime = LocalTime.MIN;

        List<LocalTime> localTimeList = new ArrayList<>();

        while (true) {
            localTimeList.add(queryLocalTime);
            queryLocalTime = queryLocalTime.plusMinutes(5);

            if (queryLocalTime.equals(queryEndTime)) {
                localTimeList.add(queryLocalTime);
                break;
            }
        }
        return localTimeList;
    }
}
