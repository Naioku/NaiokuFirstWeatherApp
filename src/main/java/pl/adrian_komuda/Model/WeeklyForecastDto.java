package pl.adrian_komuda.Model;

import pl.adrian_komuda.HelpingMethods;

public class WeeklyForecastDto {
    private long dateTime = 0;
    private int dayTemperature = 0;
    private int nightTemperature = 0;
    private int minTemperature = 0;
    private int maxTemperature = 0;
    private int dayFeelsLike = 0;
    private int nightFeelsLike = 0;
    private int pressure = 0;
    private int humidity = 0;
    private int windSpeed = 0;
    private String description = "";
    private String icon = "";

    private final String temperatureUnit = "°C";
    private final String pressureUnit = "hPa";
    private final String humidityUnit = "%";
    private final String windSpeedUnit = "km/h";

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
