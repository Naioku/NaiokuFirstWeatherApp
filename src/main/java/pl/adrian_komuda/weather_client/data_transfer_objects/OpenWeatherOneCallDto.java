package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherOneCallDto {

    @JsonProperty("hourly")
    private OpenWeatherHourlyDto[] hourly;

    @JsonProperty("timezone_offset")
    private long timezone_offset;

    public OpenWeatherHourlyDto[] getHourly() {
        return hourly;
    }

    public long getTimezone_offset() {
        return timezone_offset;
    }
}
