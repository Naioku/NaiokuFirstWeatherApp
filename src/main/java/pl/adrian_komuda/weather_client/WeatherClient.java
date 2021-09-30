package pl.adrian_komuda.weather_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.web.client.RestTemplate;
import pl.adrian_komuda.Config;
import pl.adrian_komuda.Debug;
import pl.adrian_komuda.model.*;
import pl.adrian_komuda.weather_client.data_transfer_objects.*;

import java.util.ArrayList;
import java.util.List;

public class WeatherClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    private final String GEO_URL = "http://api.openweathermap.org/geo/1.0/";

    private City lastCheckedCity;

    public WeatherClient() {}

    public City getLastCheckedCity() {
        return lastCheckedCity;
    }

    public WeatherDto getCurrentWeatherData(City city) {
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

        lastCheckedCity = city;
        return weatherDto;
    }

    public List<HourlyWeatherDto> getHourlyWeatherForecastData(City city) {
        String jsonResponse = restTemplate.getForObject(
                WEATHER_URL + "onecall?lat={lat}&lon={lon}&exclude=current,minutely,daily,alerts&appid={apiKey}&units=metric",
                String.class,
                city.getLatitude(),
                city.getLongitude(),
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

        lastCheckedCity = city;
        return hourlyWeatherDtos;
    }

    public List<WeeklyForecastDto> getWeeklyWeatherForecastData(City city) {
        String jsonResponse = restTemplate.getForObject(
                WEATHER_URL + "onecall?lat={lat}&lon={lon}&exclude=current,minutely,alerts&appid={apiKey}&units=metric",
                String.class,
                city.getLatitude(),
                city.getLongitude(),
                Config.API_KEY
        );

        List<WeeklyForecastDto> weeklyForecastDtos = new ArrayList<>();

        try {
            OpenWeatherOneCallDto openWeatherOneCallDto = new ObjectMapper().readValue(jsonResponse, OpenWeatherOneCallDto.class);
            for (OpenWeatherDailyDto openWeatherDto : openWeatherOneCallDto.getDaily()) {
                WeeklyForecastDto weeklyForecastDto = new WeeklyForecastDto(
                        openWeatherDto.getDt(), openWeatherOneCallDto.getTimezone_offset(),
                        openWeatherDto.getDayTemp(),
                        openWeatherDto.getNightTemp(),
                        openWeatherDto.getMinTemp(),
                        openWeatherDto.getMaxTemp(),
                        openWeatherDto.getDayFeelsLike(),
                        openWeatherDto.getNightFeelsLike(),
                        openWeatherDto.getPressure(),
                        openWeatherDto.getHumidity(),
                        openWeatherDto.getWindSpeed(),
                        openWeatherDto.getDescription(),
                        openWeatherDto.getIcon()
                );

                weeklyForecastDtos.add(weeklyForecastDto);
            }

        } catch (JsonProcessingException e) {
            System.out.println("Error in converting json to object!");
            e.printStackTrace();
        }

        lastCheckedCity = city;
        return weeklyForecastDtos;
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
