package pl.adrian_komuda.weather_client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherWindDto {
    private float speed;

    public float getSpeed() {
        return speed;
    }
}
