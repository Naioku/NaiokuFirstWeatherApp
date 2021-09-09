package pl.adrian_komuda.weather_client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherWeatherDto {

    private OpenWeatherMainDto main;
    private OpenWeatherWindDto wind;

    public OpenWeatherMainDto getMain() {
        return main;
    }

    public OpenWeatherWindDto getWind() {
        return wind;
    }
}
