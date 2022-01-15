package pl.adrian_komuda.weather_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import pl.adrian_komuda.utilities.custom_exceptions.ApiException;
import pl.adrian_komuda.weather_client.api_dtos.OpenWeatherHourlyDto;
import pl.adrian_komuda.weather_client.api_dtos.OpenWeatherOneCallDto;
import pl.adrian_komuda.weather_client.api_dtos.OpenWeatherWeatherDto;
import pl.adrian_komuda.weather_client.my_dtos.City;
import pl.adrian_komuda.weather_client.my_dtos.HourlyWeatherDto;
import pl.adrian_komuda.weather_client.my_dtos.SpecificCity;
import pl.adrian_komuda.weather_client.my_dtos.WeatherDto;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class WeatherClientTest {

    RestTemplate restTemplate = mock(RestTemplate.class);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    City city = new SpecificCity("Nagoya", 0F, 0F);

    WeatherClient weatherClient = new WeatherClient(restTemplate, objectMapper);

    @Test
    void whenNoExceptionOccurredGetCurrentWeatherDataShouldReturnProperWeatherDto() throws JsonProcessingException, ApiException {
        // given
        OpenWeatherWeatherDto openWeatherWeatherDto = mock(OpenWeatherWeatherDto.class);

        given(restTemplate.getForObject(any(String.class), any(Class.class), any(City.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(openWeatherWeatherDto.getTemp()).willReturn(StaticData.TEMPERATURE);
        given(openWeatherWeatherDto.getPressure()).willReturn(StaticData.PRESSURE);
        given(openWeatherWeatherDto.getHumidity()).willReturn(StaticData.HUMIDITY);
        given(openWeatherWeatherDto.getSpeed()).willReturn(StaticData.WIND_SPEED);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherWeatherDto.class)).willReturn(openWeatherWeatherDto);

        WeatherDto properAnswer = new WeatherDto(StaticData.TEMPERATURE, StaticData.PRESSURE, StaticData.HUMIDITY, StaticData.WIND_SPEED);

        // when
        WeatherDto response = weatherClient.getCurrentWeatherData(city);

        // then
        assertThat(response).isEqualTo(properAnswer);
    }

    @Test
    void whenJsonProcessingExceptionIsThrownApiExceptionShouldBeThrown() throws JsonProcessingException, ApiException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(City.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherWeatherDto.class)).willThrow(JsonProcessingException.class);

        // when
        // then
        assertThrows(ApiException.class, () ->  weatherClient.getCurrentWeatherData(city));
    }

    @Test
    void whenNoExceptionOccurredGetHourlyWeatherForecastDataShouldReturnProperWeatherDto() throws JsonProcessingException, ApiException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(City.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);

        OpenWeatherOneCallDto openWeatherOneCallDto = mock(OpenWeatherOneCallDto.class);
        OpenWeatherHourlyDto[] openWeatherHourlyDtos = getArrayOfOpenWeatherHourlyDtos();
        given(openWeatherOneCallDto.getHourly()).willReturn(openWeatherHourlyDtos);

        System.out.println(Arrays.toString(openWeatherHourlyDtos)); // here is ok
        System.out.println(openWeatherHourlyDtos[0].getIcon()); // here is ok
        System.out.println(objectMapper); // here is ok

        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)).willReturn(openWeatherOneCallDto);

        System.out.println(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)); // here is ok

        weatherClient = new WeatherClient(restTemplate, objectMapper); // Even after creating new obj. it doesn't work

        // when
        List<HourlyWeatherDto> response = weatherClient.getHourlyWeatherForecastData(city);

        // then
        assertThat(response.get(0).getDateTime()).isEqualTo(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_1);
        assertThat(response.get(0).getTemperature()).isEqualTo(HourlyWeatherDtoStaticData.TEMPERATURE_1);
        assertThat(response.get(0).getUvi()).isEqualTo(HourlyWeatherDtoStaticData.UVI_1);
        assertThat(response.get(0).getDescription()).isEqualTo(HourlyWeatherDtoStaticData.DESCRIPTION_1);
        assertThat(response.get(0).getIcon()).isEqualTo(HourlyWeatherDtoStaticData.ICON_1);

        assertThat(response.get(1).getDateTime()).isEqualTo(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_2);
        assertThat(response.get(1).getTemperature()).isEqualTo(HourlyWeatherDtoStaticData.TEMPERATURE_2);
        assertThat(response.get(1).getUvi()).isEqualTo(HourlyWeatherDtoStaticData.UVI_2);
        assertThat(response.get(1).getDescription()).isEqualTo(HourlyWeatherDtoStaticData.DESCRIPTION_2);
        assertThat(response.get(1).getIcon()).isEqualTo(HourlyWeatherDtoStaticData.ICON_2);
    }

    private OpenWeatherHourlyDto[] getArrayOfOpenWeatherHourlyDtos() {
        OpenWeatherHourlyDto openWeatherHourlyDto1 = mock(OpenWeatherHourlyDto.class);
        given(openWeatherHourlyDto1.getDt()).willReturn(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_1);
        given(openWeatherHourlyDto1.getTemp()).willReturn(HourlyWeatherDtoStaticData.TEMPERATURE_1);
        given(openWeatherHourlyDto1.getUvi()).willReturn(HourlyWeatherDtoStaticData.UVI_1);
        given(openWeatherHourlyDto1.getDescription()).willReturn(HourlyWeatherDtoStaticData.DESCRIPTION_1);
        given(openWeatherHourlyDto1.getIcon()).willReturn(HourlyWeatherDtoStaticData.ICON_1);

        OpenWeatherHourlyDto openWeatherHourlyDto2 = mock(OpenWeatherHourlyDto.class);
        given(openWeatherHourlyDto2.getDt()).willReturn(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_2);
        given(openWeatherHourlyDto2.getTemp()).willReturn(HourlyWeatherDtoStaticData.TEMPERATURE_2);
        given(openWeatherHourlyDto2.getUvi()).willReturn(HourlyWeatherDtoStaticData.UVI_2);
        given(openWeatherHourlyDto2.getDescription()).willReturn(HourlyWeatherDtoStaticData.DESCRIPTION_2);
        given(openWeatherHourlyDto2.getIcon()).willReturn(HourlyWeatherDtoStaticData.ICON_2);

        return new OpenWeatherHourlyDto[]{openWeatherHourlyDto1, openWeatherHourlyDto2};
    }


    @Test
    void afterCheckingWeatherDataLastCheckedCityFieldShouldNotBeNull() {
        // given

    }

}