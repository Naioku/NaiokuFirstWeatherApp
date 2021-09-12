package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherOneCallDto {

    @JsonProperty("hourly")
    private OpenWeatherHourlyDto[] hourly;

    public OpenWeatherHourlyDto[] getHourly() {
        return hourly;
    }
}
