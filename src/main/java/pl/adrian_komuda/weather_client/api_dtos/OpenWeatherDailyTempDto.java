package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDailyTempDto {

    private final float day;
    private final float min;
    private final float max;
    private final float night;

    @JsonCreator
    public OpenWeatherDailyTempDto(@JsonProperty("day")float day, @JsonProperty("min")float min, @JsonProperty("max")float max, @JsonProperty("night")float night) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
    }

    public float getDay() {
        return day;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public float getNight() {
        return night;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherDailyTempDto that = (OpenWeatherDailyTempDto) o;
        return Float.compare(that.day, day) == 0 && Float.compare(that.min, min) == 0 && Float.compare(that.max, max) == 0 && Float.compare(that.night, night) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, min, max, night);
    }
}
