package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherGeocodingCityDto {

    @JsonProperty("lat")
    private float lat;

    @JsonProperty("lon")
    private float lon;

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }
}
