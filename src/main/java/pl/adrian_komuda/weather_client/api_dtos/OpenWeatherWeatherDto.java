package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherWeatherDto {

    private final OpenWeatherMainDto main;
    private final OpenWeatherWindDto wind;

    @JsonCreator
    public OpenWeatherWeatherDto(@JsonProperty("main")OpenWeatherMainDto main, @JsonProperty("wind")OpenWeatherWindDto wind) {
        this.main = main;
        this.wind = wind;
    }

    public float getTemp() {
        return main.getTemp();
    }

    public int getPressure() {
        return main.getPressure();
    }

    public int getHumidity() {
        return main.getHumidity();
    }

    public float getSpeed() {
        return wind.getSpeed();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherWeatherDto that = (OpenWeatherWeatherDto) o;
        return Objects.equals(main, that.main) && Objects.equals(wind, that.wind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(main, wind);
    }
}
