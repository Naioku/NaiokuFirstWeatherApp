package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMainDto {

    private final float temp;
    private final int pressure;
    private final int humidity;

    @JsonCreator
    public OpenWeatherMainDto(
            @JsonProperty("temp")float temp,
            @JsonProperty("pressure")int pressure,
            @JsonProperty("humidity")int humidity
    ) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public float getTemp() {
        return temp;
    }

    int getPressure() {
        return pressure;
    }

    int getHumidity() {
        return humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherMainDto that = (OpenWeatherMainDto) o;
        return Float.compare(that.temp, temp) == 0 && pressure == that.pressure && humidity == that.humidity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temp, pressure, humidity);
    }
}
