package pl.adrian_komuda.weather_client.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDailyDto {

    @JsonProperty("dt")
    private long dt;

    @JsonProperty("temp")
    private OpenWeatherDailyTempDto temp;

    @JsonProperty("feels_like")
    private OpenWeatherDailyFeelsLikeDto feels_like;

    @JsonProperty("pressure")
    private int pressure;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("wind_speed")
    private float wind_speed;

    @JsonProperty("weather")
    private OpenWeatherHourlyAndDailyWeatherDto[] weather;

    public long getDt() {
        return dt;
    }

    public float getDayTemp() {
        return temp.getDay();
    }

    public float getMinTemp() {
        return temp.getMin();
    }

    public float getMaxTemp() {
        return temp.getMax();
    }

    public float getNightTemp() {
        return temp.getNight();
    }

    public float getDayFeelsLike() {
        return feels_like.getDay();
    }

    public float getNightFeelsLike() {
        return feels_like.getNight();
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return wind_speed;
    }

    public String getDescription() {
        return weather[0].getDescription();
    }

    public String getIcon() {
        return weather[0].getIcon();
    }


    // Without this getter, "main" object will be null. Jason must require it...
    public OpenWeatherDailyTempDto getTemp() {
        return temp;
    }

    // Without this getter, "main" object will be null. Jason must require it...
    public OpenWeatherDailyFeelsLikeDto getFeels_like() {
        return feels_like;
    }

    // Without this getter, "main" object will be null. Jason must require it...
    public OpenWeatherHourlyAndDailyWeatherDto[] getWeather() {
        return weather;
    }
}
