package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDailyFeelsLikeDto {

    private final float day;
    private final float night;

    @JsonCreator
    public OpenWeatherDailyFeelsLikeDto(@JsonProperty("day")float day, @JsonProperty("night")float night) {
        this.day = day;
        this.night = night;
    }

    public float getDay() {
        return day;
    }

    public float getNight() {
        return night;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherDailyFeelsLikeDto that = (OpenWeatherDailyFeelsLikeDto) o;
        return Float.compare(that.day, day) == 0 && Float.compare(that.night, night) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, night);
    }
}
