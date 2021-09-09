package pl.adrian_komuda.weather_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import pl.adrian_komuda.Config;
import pl.adrian_komuda.model.WeatherDto;
import pl.adrian_komuda.weather_client.dto.OpenWeatherWeatherDto;

public class WeatherClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";

    public WeatherClient() {}

    public WeatherDto getCurrentWeatherForCity(String city) {
        String jsonResponse = restTemplate.getForObject(
                WEATHER_URL + "weather?q={city}&appid={apiKey}&units=metric",
                String.class,
                city,
                Config.API_KEY);

        WeatherDto weatherDto = new WeatherDto();

        try {
            OpenWeatherWeatherDto openWeatherWeatherDto = new ObjectMapper().readValue(jsonResponse, OpenWeatherWeatherDto.class);
            weatherDto.setTemperature(openWeatherWeatherDto.getMain().getTemp());
            weatherDto.setPressure(openWeatherWeatherDto.getMain().getPressure());
            weatherDto.setHumidity(openWeatherWeatherDto.getMain().getHumidity());
            weatherDto.setWindSpeed(openWeatherWeatherDto.getWind().getSpeed());

        } catch (JsonProcessingException e) {
            System.out.println("Error in converting json to object!");
            e.printStackTrace();
        }

        return weatherDto;
    }
}
