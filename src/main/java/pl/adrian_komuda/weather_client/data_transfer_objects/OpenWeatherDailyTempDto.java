package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDailyTempDto {

    @JsonProperty("day")
    private float day;

    @JsonProperty("min")
    private float min;

    @JsonProperty("max")
    private float max;

    @JsonProperty("night")
    private float night;

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
}
