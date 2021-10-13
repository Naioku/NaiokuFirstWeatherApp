package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherHourlyAndDailyWeatherDto {

    private final String description;
    private final String icon;

    @JsonCreator
    public OpenWeatherHourlyAndDailyWeatherDto(@JsonProperty("description")String description, @JsonProperty("icon")String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherHourlyAndDailyWeatherDto that = (OpenWeatherHourlyAndDailyWeatherDto) o;
        return Objects.equals(description, that.description) && Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, icon);
    }
}
