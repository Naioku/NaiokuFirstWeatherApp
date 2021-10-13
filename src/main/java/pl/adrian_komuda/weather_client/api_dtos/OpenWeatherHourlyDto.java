package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherHourlyDto {

    private final long dt;
    private final float temp;
    private final int uvi;
    private final OpenWeatherHourlyAndDailyWeatherDto[] weather;

    @JsonCreator
    public OpenWeatherHourlyDto(
            @JsonProperty("dt")long dt,
            @JsonProperty("temp")float temp,
            @JsonProperty("uvi")int uvi,
            @JsonProperty("weather")OpenWeatherHourlyAndDailyWeatherDto[] weather
    ) {
        this.dt = dt;
        this.temp = temp;
        this.uvi = uvi;
        this.weather = weather;
    }

    public long getDt() {
        return dt;
    }

    public float getTemp() {
        return temp;
    }

    public int getUvi() {
        return uvi;
    }

    public String getDescription() {
        return weather[0].getDescription();
    }

    public String getIcon() {
        return weather[0].getIcon();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherHourlyDto that = (OpenWeatherHourlyDto) o;
        return dt == that.dt && Float.compare(that.temp, temp) == 0 && uvi == that.uvi && Arrays.equals(weather, that.weather);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dt, temp, uvi);
        result = 31 * result + Arrays.hashCode(weather);
        return result;
    }
}
