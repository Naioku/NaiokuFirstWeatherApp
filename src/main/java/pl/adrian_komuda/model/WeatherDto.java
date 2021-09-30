package pl.adrian_komuda.model;

import pl.adrian_komuda.HelpingMethods;

public class WeatherDto {
    private int temperature = 0;
    private int pressure = 0;
    private int humidity = 0;
    private int windSpeed = 0;

    private final String temperatureUnit = "°C";
    private final String pressureUnit = "hPa";
    private final String humidityUnit = "%";
    private final String windSpeedUnit = "km/h";

    public WeatherDto() {
    }

    public void setTemperature(float temperature) {
        temperature = Math.round(temperature);
        this.temperature = (int) temperature;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(float windSpeed) {
        windSpeed = HelpingMethods.mPerSecToKmPerH(windSpeed);
        windSpeed = Math.round(windSpeed);
        this.windSpeed = (int) windSpeed;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
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

    @Override
    public String toString() {
        return
                "Temperature: " + temperature + " " + temperatureUnit +
                "\nPressure: " + pressure + " " + pressureUnit +
                "\nHumidity: " + humidity + " " + humidityUnit +
                "\nWind speed: " + windSpeed + " " + windSpeedUnit;
    }
}
