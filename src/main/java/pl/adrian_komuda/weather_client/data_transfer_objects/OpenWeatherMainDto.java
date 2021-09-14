package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMainDto {
    @JsonProperty("temp")
    private float temp;

    @JsonProperty("pressure")
    private int pressure;

    @JsonProperty("humidity")
    private int humidity;

    public float getTemp() {
        return temp;
    }

    int getPressure() {
        return pressure;
    }

    int getHumidity() {
        return humidity;
    }
}
