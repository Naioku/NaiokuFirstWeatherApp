package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDailyFeelsLikeDto {

    @JsonProperty("day")
    private float day;

    @JsonProperty("night")
    private float night;

    public float getDay() {
        return day;
    }

    public float getNight() {
        return night;
    }
}
