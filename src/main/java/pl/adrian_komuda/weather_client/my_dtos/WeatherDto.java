package pl.adrian_komuda.weather_client.my_dtos;

import pl.adrian_komuda.utilities.HelpingMethods;
import pl.adrian_komuda.utilities.UnitsSymbols;

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
}
