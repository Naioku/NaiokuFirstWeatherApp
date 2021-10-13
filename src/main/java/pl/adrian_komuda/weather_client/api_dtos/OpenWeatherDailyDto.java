package pl.adrian_komuda.weather_client.api_dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDailyDto {

    private final long dt;
    private final OpenWeatherDailyTempDto temp;
    private final OpenWeatherDailyFeelsLikeDto feels_like;
    private final int pressure;
    private final int humidity;
    private final float wind_speed;
    private final OpenWeatherHourlyAndDailyWeatherDto[] weather;

    @JsonCreator
    public OpenWeatherDailyDto(
            @JsonProperty("dt") long dt,
            @JsonProperty("temp")OpenWeatherDailyTempDto temp,
            @JsonProperty("feels_like")OpenWeatherDailyFeelsLikeDto feels_like,
            @JsonProperty("pressure")int pressure,
            @JsonProperty("humidity")int humidity,
            @JsonProperty("wind_speed")float wind_speed,
            @JsonProperty("weather")OpenWeatherHourlyAndDailyWeatherDto[] weather
    ) {
        this.dt = dt;
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.weather = weather;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherDailyDto that = (OpenWeatherDailyDto) o;
        return dt == that.dt && pressure == that.pressure && humidity == that.humidity && Float.compare(that.wind_speed, wind_speed) == 0 && Objects.equals(temp, that.temp) && Objects.equals(feels_like, that.feels_like) && Arrays.equals(weather, that.weather);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dt, temp, feels_like, pressure, humidity, wind_speed);
        result = 31 * result + Arrays.hashCode(weather);
        return result;
    }
}
