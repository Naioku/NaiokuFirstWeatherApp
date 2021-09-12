package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.adrian_komuda.Debug;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherHourlyDto {

    @JsonProperty("dt")
    private long dt;

    @JsonProperty("temp")
    private float temp;

    @JsonProperty("uvi")
    private int uvi;

    @JsonProperty("weather")
    private OpenWeatherHourlyWeatherDto[] weather;

    public long getDt() {
        return dt;
    }

    public float getTemp() {
        return temp;
    }

    public int getUvi() {
        return uvi;
    }

    public String getDescription() {
        return weather[0].getDescription();
    }

    public String getIcon() {
        return weather[0].getIcon();
    }

    // Without this getter, "main" object will be null. Jason must require it...
    public OpenWeatherHourlyWeatherDto[] getWeather() {
        return weather;
    }
}
