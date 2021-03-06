package pl.adrian_komuda.weather_client.my_dtos;

import pl.adrian_komuda.utilities.HelpingMethods;
import pl.adrian_komuda.utilities.UnitsSymbols;

import java.util.Objects;

public class WeatherDto {
    private final int temperature;
    private final int pressure;
    private final int humidity;
    private final int windSpeed;

    private final String temperatureUnit = UnitsSymbols.TEMPERATURE;
    private final String pressureUnit = UnitsSymbols.PRESSURE;
    private final String humidityUnit = UnitsSymbols.HUMIDITY;
    private final String windSpeedUnit = UnitsSymbols.WIND_SPEED;

    public WeatherDto(float temperature, int pressure, int humidity, float windSpeed) {
        this.temperature = Math.round(temperature);
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = Math.round(HelpingMethods.mPerSecToKmPerH(windSpeed));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherDto that = (WeatherDto) o;
        return temperature == that.temperature && pressure == that.pressure && humidity == that.humidity && windSpeed == that.windSpeed && Objects.equals(temperatureUnit, that.temperatureUnit) && Objects.equals(pressureUnit, that.pressureUnit) && Objects.equals(humidityUnit, that.humidityUnit) && Objects.equals(windSpeedUnit, that.windSpeedUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, pressure, humidity, windSpeed, temperatureUnit, pressureUnit, humidityUnit, windSpeedUnit);
    }
}
