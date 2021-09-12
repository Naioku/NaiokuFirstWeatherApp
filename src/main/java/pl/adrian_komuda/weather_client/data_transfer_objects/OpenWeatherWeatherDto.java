package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.adrian_komuda.Debug;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherWeatherDto {

    @JsonProperty("main")
    private OpenWeatherMainDto main;

    @JsonProperty("wind")
    private OpenWeatherWindDto wind;

    public float getTemp() {
        return main.getTemp();
    }

    // Without this getter, "main" object will be null. Jason must require it...
    public OpenWeatherMainDto getMain() {
        return main;
    }

    // Without this getter, "main" object will be null. Jason must require it...
    public OpenWeatherWindDto getWind() {
        return wind;
    }

    public int getPressure() {
        return main.getPressure();
    }

    public int getHumidity() {
        return main.getHumidity();
    }

    public float getSpeed() {
        return wind.getSpeed();
    }
}
