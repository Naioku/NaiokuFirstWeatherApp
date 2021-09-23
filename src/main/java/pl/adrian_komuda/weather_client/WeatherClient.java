package pl.adrian_komuda.weather_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.web.client.RestTemplate;
import pl.adrian_komuda.Config;
import pl.adrian_komuda.model.*;
import pl.adrian_komuda.weather_client.data_transfer_objects.OpenWeatherGeocodingCityDto;
import pl.adrian_komuda.weather_client.data_transfer_objects.OpenWeatherHourlyDto;
import pl.adrian_komuda.weather_client.data_transfer_objects.OpenWeatherOneCallDto;
import pl.adrian_komuda.weather_client.data_transfer_objects.OpenWeatherWeatherDto;

import java.util.ArrayList;
import java.util.List;

public class WeatherClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    private final String GEO_URL = "http://api.openweathermap.org/geo/1.0/";

    private WeatherDto lastCurrentWeatherData;
    private List<HourlyWeatherDto> lastHourlyWeatherData;

    public WeatherClient() {}

    public WeatherDto getCurrentWeatherData(String city) {
        String jsonResponse = restTemplate.getForObject(
                WEATHER_URL + "weather?q={city}&appid={apiKey}&units=metric",
                String.class,
                city,
                Config.API_KEY
        );

        WeatherDto weatherDto = new WeatherDto();

        try {
            OpenWeatherWeatherDto openWeatherWeatherDto = new ObjectMapper().readValue(jsonResponse, OpenWeatherWeatherDto.class);
            weatherDto.setTemperature(openWeatherWeatherDto.getTemp());
            weatherDto.setPressure(openWeatherWeatherDto.getPressure());
            weatherDto.setHumidity(openWeatherWeatherDto.getHumidity());
            weatherDto.setWindSpeed(openWeatherWeatherDto.getSpeed());

        } catch (JsonProcessingException e) {
            System.out.println("Error in converting json to object!");
            e.printStackTrace();
        }

        lastCurrentWeatherData = weatherDto;
        return weatherDto;
    }

    public WeatherDto getLastCurrentWeatherData() {
        return lastCurrentWeatherData;
    }

    public List<HourlyWeatherDto> getHourlyWeatherForecastData(float latitude, float longitude) {
        String jsonResponse = restTemplate.getForObject(
                WEATHER_URL + "onecall?lat={lat}&lon={lon}&exclude=current,minutely,daily,alerts&appid={apiKey}&units=metric",
                String.class,
                latitude,
                longitude,
                Config.API_KEY
        );

        List<HourlyWeatherDto> hourlyWeatherDtos = new ArrayList<>();

        try {
            OpenWeatherOneCallDto openWeatherOneCallDto = new ObjectMapper().readValue(jsonResponse, OpenWeatherOneCallDto.class);
            for (OpenWeatherHourlyDto openWeatherDto : openWeatherOneCallDto.getHourly()) {
                HourlyWeatherDto hourlyWeatherDto = new HourlyWeatherDto(
                        openWeatherDto.getDt(),
                        openWeatherOneCallDto.getTimezone_offset(),
                        openWeatherDto.getTemp(),
                        openWeatherDto.getUvi(),
                        openWeatherDto.getDescription(),
                        openWeatherDto.getIcon()
                );

                hourlyWeatherDtos.add(hourlyWeatherDto);
            }

        } catch (JsonProcessingException e) {
            System.out.println("Error in converting json to object!");
            e.printStackTrace();
        }

        lastHourlyWeatherData = hourlyWeatherDtos;
        return hourlyWeatherDtos;
    }

    public List<HourlyWeatherDto> getLastHourlyWeatherForecastData() {
        return lastHourlyWeatherData;
    }

    public City getCityInfo(String city) {
        String jsonResponse = restTemplate.getForObject(
                GEO_URL + "direct?q={city}&appid={apiKey}",
                String.class,
                city,
                Config.API_KEY
        );

        City cityObj = new SpecificCity();

        try {
            OpenWeatherGeocodingCityDto[] openWeatherGeocodingCityDto = new ObjectMapper().readValue(jsonResponse, OpenWeatherGeocodingCityDto[].class);
            cityObj.setName(city);
            cityObj.setLatitude(openWeatherGeocodingCityDto[0].getLat());
            cityObj.setLongitude(openWeatherGeocodingCityDto[0].getLon());

        } catch (JsonProcessingException e) {
            System.out.println("Error in converting json to object!");
            cityObj = new EmptyCity();
            e.printStackTrace();
        }

        return cityObj;
    }

    public City getCityInfo(String city, String countryISOCode) throws ArrayIndexOutOfBoundsException {
        String jsonResponse = restTemplate.getForObject(
                GEO_URL + "direct?q={city},{countryCode}&appid={apiKey}",
                String.class,
                city,
                countryISOCode,
                Config.API_KEY
        );

        City cityObj = new SpecificCity();

        try {
            OpenWeatherGeocodingCityDto[] openWeatherGeocodingCityDto = new ObjectMapper().readValue(jsonResponse, OpenWeatherGeocodingCityDto[].class);
            cityObj.setName(city);
            cityObj.setLatitude(openWeatherGeocodingCityDto[0].getLat());
            cityObj.setLongitude(openWeatherGeocodingCityDto[0].getLon());

        } catch (JsonProcessingException e) {
            System.out.println("Error in converting json to object!");
            cityObj = new EmptyCity();
            e.printStackTrace();
        }

        return cityObj;
    }

    public ImageView getWeatherIcon(String code) {
        return new ImageView(new Image("http://openweathermap.org/img/wn/" + code + "@2x.png"));
    }
}
