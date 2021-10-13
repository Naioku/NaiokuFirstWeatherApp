package pl.adrian_komuda.weather_client.my_dtos;

import pl.adrian_komuda.utilities.HelpingMethods;
import pl.adrian_komuda.utilities.UnitsSymbols;

public class WeeklyForecastDto {
    private final long dateTime;
    private final int dayTemperature;
    private final int nightTemperature;
    private final int minTemperature;
    private final int maxTemperature;
    private final int dayFeelsLike;
    private final int nightFeelsLike;
    private final int pressure;
    private final int humidity;
    private final int windSpeed;
    private final String description;
    private final String icon;

    private final String temperatureUnit = UnitsSymbols.TEMPERATURE;
    private final String pressureUnit = UnitsSymbols.PRESSURE;
    private final String humidityUnit = UnitsSymbols.HUMIDITY;
    private final String windSpeedUnit = UnitsSymbols.WIND_SPEED;

    public WeeklyForecastDto(
            long dateTime, long dateTimeOffset,
            float dayTemperature,
            float nightTemperature,
            float minTemperature,
            float maxTemperature,
            float dayFeelsLike,
            float nightFeelsLike,
            int pressure,
            int humidity,
            float windSpeed,
            String description,
            String icon
    ) {
        this.dateTime = dateTime + dateTimeOffset;
        this.dayTemperature = Math.round(dayTemperature);
        this.nightTemperature = Math.round(nightTemperature);
        this.minTemperature = Math.round(minTemperature);
        this.maxTemperature = Math.round(maxTemperature);
        this.dayFeelsLike = Math.round(dayFeelsLike);
        this.nightFeelsLike = Math.round(nightFeelsLike);
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = Math.round(HelpingMethods.mPerSecToKmPerH(windSpeed));
        this.description = description;
        this.icon = icon;
    }

    public long getDateTime() {
        return dateTime;
    }

    public int getDayTemperature() {
        return dayTemperature;
    }

    public int getNightTemperature() {
        return nightTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getDayFeelsLike() {
        return dayFeelsLike;
    }

    public int getNightFeelsLike() {
        return nightFeelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getPressureUnit() {
        return pressureUnit;
    }

    public String getHumidityUnit() {
        return humidityUnit;
    }

    public String getWindSpeedUnit() {
        return windSpeedUnit;
    }
}
