package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherOneCallDto {

    private final OpenWeatherHourlyDto[] hourly;
    private final long timezone_offset;
    private final OpenWeatherDailyDto[] daily;

    @JsonCreator
    public OpenWeatherOneCallDto(
            @JsonProperty("hourly")OpenWeatherHourlyDto[] hourly,
            @JsonProperty("timezone_offset")long timezone_offset,
            @JsonProperty("daily")OpenWeatherDailyDto[] daily
    ) {
        this.hourly = hourly;
        this.timezone_offset = timezone_offset;
        this.daily = daily;
    }

    public OpenWeatherHourlyDto[] getHourly() {
        return hourly;
    }

    public long getTimezone_offset() {
        return timezone_offset;
    }

    public OpenWeatherDailyDto[] getDaily() {
        return daily;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherOneCallDto that = (OpenWeatherOneCallDto) o;
        return timezone_offset == that.timezone_offset && Arrays.equals(hourly, that.hourly) && Arrays.equals(daily, that.daily);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(timezone_offset);
        result = 31 * result + Arrays.hashCode(hourly);
        result = 31 * result + Arrays.hashCode(daily);
        return result;
    }
}
