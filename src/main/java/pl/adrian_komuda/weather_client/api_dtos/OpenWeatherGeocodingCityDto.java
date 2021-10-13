package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherGeocodingCityDto {

    private final float lat;
    private final float lon;

    @JsonCreator
    public OpenWeatherGeocodingCityDto(@JsonProperty("lat")float lat, @JsonProperty("lon")float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherGeocodingCityDto that = (OpenWeatherGeocodingCityDto) o;
        return Float.compare(that.lat, lat) == 0 && Float.compare(that.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
