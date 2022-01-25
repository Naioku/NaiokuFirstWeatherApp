package pl.adrian_komuda.weather_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import pl.adrian_komuda.utilities.custom_exceptions.ApiException;
import pl.adrian_komuda.weather_client.api_dtos.*;
import pl.adrian_komuda.weather_client.my_dtos.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class WeatherClientTest {

    private RestTemplate restTemplate = mock(RestTemplate.class);
    private ObjectMapper objectMapper = mock(ObjectMapper.class);
    private final static City CITY = new SpecificCity("Nagoya", 0F, 0F);

    private WeatherClient weatherClient = new WeatherClient(restTemplate, objectMapper);

    @Tag("MyLogic")
    @Test
    void whenNoExceptionOccurredGetCurrentWeatherDataShouldReturnProperWeatherDto() throws JsonProcessingException, ApiException {
        // given
        OpenWeatherWeatherDto openWeatherWeatherDto = getOpenWeatherWeatherDto();

        given(restTemplate.getForObject(any(String.class), any(Class.class), any(City.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherWeatherDto.class)).willReturn(openWeatherWeatherDto);

        WeatherDto properAnswer = new WeatherDto(StaticData.TEMPERATURE, StaticData.PRESSURE, StaticData.HUMIDITY, StaticData.WIND_SPEED);

        // when
        WeatherDto response = weatherClient.getCurrentWeatherData(CITY);

        // then
        assertThat(response).isEqualTo(properAnswer);
    }

    private OpenWeatherWeatherDto getOpenWeatherWeatherDto() {
        OpenWeatherWeatherDto openWeatherWeatherDto = mock(OpenWeatherWeatherDto.class);
        given(openWeatherWeatherDto.getTemp()).willReturn(StaticData.TEMPERATURE);
        given(openWeatherWeatherDto.getPressure()).willReturn(StaticData.PRESSURE);
        given(openWeatherWeatherDto.getHumidity()).willReturn(StaticData.HUMIDITY);
        given(openWeatherWeatherDto.getSpeed()).willReturn(StaticData.WIND_SPEED);

        return openWeatherWeatherDto;
    }

    @Tag("MyLogic")
    @Test
    void whenJsonProcessingExceptionIsThrownInGetCurrentWeatherDataApiExceptionShouldBeThrown() throws JsonProcessingException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(City.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherWeatherDto.class)).willThrow(JsonProcessingException.class);

        // when
        // then
        assertThrows(ApiException.class, () ->  weatherClient.getCurrentWeatherData(CITY));
    }

    @Tag("MyLogic")
    @Test
    void afterGetCurrentWeatherDataLastCheckedCityFieldShouldBeExactlyForWhichYouCheckedWeather() throws JsonProcessingException, ApiException {
        // given
        OpenWeatherWeatherDto openWeatherWeatherDto = getOpenWeatherWeatherDto();

        given(restTemplate.getForObject(any(String.class), any(Class.class), any(City.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherWeatherDto.class)).willReturn(openWeatherWeatherDto);

        WeatherDto properAnswer = new WeatherDto(StaticData.TEMPERATURE, StaticData.PRESSURE, StaticData.HUMIDITY, StaticData.WIND_SPEED);

        // when
        WeatherDto response = weatherClient.getCurrentWeatherData(CITY);

        // then
        assertThat(response).isEqualTo(properAnswer);
    }

    @Tag("MyLogic")
    @Test
    void whenNoExceptionOccurredGetHourlyWeatherForecastDataShouldReturnProperWeatherDto() throws JsonProcessingException, ApiException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(float.class), any(float.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);

        OpenWeatherOneCallDto openWeatherOneCallDto = mock(OpenWeatherOneCallDto.class);
        OpenWeatherHourlyDto[] openWeatherHourlyDtos = getArrayOfOpenWeatherHourlyDtos();
        given(openWeatherOneCallDto.getHourly()).willReturn(openWeatherHourlyDtos);

        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)).willReturn(openWeatherOneCallDto);

        // when
        List<HourlyWeatherDto> response = weatherClient.getHourlyWeatherForecastData(CITY);

        // then
        assertThat(response.get(0).getDateTime()).isEqualTo(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_1);
        assertThat(response.get(0).getTemperature()).isEqualTo(HourlyWeatherDtoStaticData.TEMPERATURE_INT_1);
        assertThat(response.get(0).getUvi()).isEqualTo(HourlyWeatherDtoStaticData.UVI_1);
        assertThat(response.get(0).getDescription()).isEqualTo(HourlyWeatherDtoStaticData.DESCRIPTION_1);
        assertThat(response.get(0).getIcon()).isEqualTo(HourlyWeatherDtoStaticData.ICON_1);

        assertThat(response.get(1).getDateTime()).isEqualTo(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_2);
        assertThat(response.get(1).getTemperature()).isEqualTo(HourlyWeatherDtoStaticData.TEMPERATURE_INT_2);
        assertThat(response.get(1).getUvi()).isEqualTo(HourlyWeatherDtoStaticData.UVI_2);
        assertThat(response.get(1).getDescription()).isEqualTo(HourlyWeatherDtoStaticData.DESCRIPTION_2);
        assertThat(response.get(1).getIcon()).isEqualTo(HourlyWeatherDtoStaticData.ICON_2);
    }

    private OpenWeatherHourlyDto[] getArrayOfOpenWeatherHourlyDtos() {
        OpenWeatherHourlyDto openWeatherHourlyDto1 = mock(OpenWeatherHourlyDto.class);
        given(openWeatherHourlyDto1.getDt()).willReturn(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_1);
        given(openWeatherHourlyDto1.getTemp()).willReturn(HourlyWeatherDtoStaticData.TEMPERATURE_FLOAT_1);
        given(openWeatherHourlyDto1.getUvi()).willReturn(HourlyWeatherDtoStaticData.UVI_1);
        given(openWeatherHourlyDto1.getDescription()).willReturn(HourlyWeatherDtoStaticData.DESCRIPTION_1);
        given(openWeatherHourlyDto1.getIcon()).willReturn(HourlyWeatherDtoStaticData.ICON_1);

        OpenWeatherHourlyDto openWeatherHourlyDto2 = mock(OpenWeatherHourlyDto.class);
        given(openWeatherHourlyDto2.getDt()).willReturn(HourlyWeatherDtoStaticData.UNIX_DATE_TIME_2);
        given(openWeatherHourlyDto2.getTemp()).willReturn(HourlyWeatherDtoStaticData.TEMPERATURE_FLOAT_2);
        given(openWeatherHourlyDto2.getUvi()).willReturn(HourlyWeatherDtoStaticData.UVI_2);
        given(openWeatherHourlyDto2.getDescription()).willReturn(HourlyWeatherDtoStaticData.DESCRIPTION_2);
        given(openWeatherHourlyDto2.getIcon()).willReturn(HourlyWeatherDtoStaticData.ICON_2);

        return new OpenWeatherHourlyDto[]{openWeatherHourlyDto1, openWeatherHourlyDto2};
    }

    @Tag("MyLogic")
    @Test
    void whenJsonProcessingExceptionIsThrownInGetHourlyWeatherForecastDataApiExceptionShouldBeThrown() throws JsonProcessingException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(float.class), any(float.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)).willThrow(JsonProcessingException.class);

        // when
        // then
        assertThrows(ApiException.class, () ->  weatherClient.getHourlyWeatherForecastData(CITY));
    }

    @Tag("MyLogic")
    @Test
    void afterGetHourlyWeatherForecastDataLastCheckedCityFieldShouldBeExactlyForWhichYouCheckedWeather() throws ApiException, JsonProcessingException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(float.class), any(float.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);

        OpenWeatherOneCallDto openWeatherOneCallDto = mock(OpenWeatherOneCallDto.class);
        OpenWeatherHourlyDto[] openWeatherHourlyDtos = getArrayOfOpenWeatherHourlyDtos();
        given(openWeatherOneCallDto.getHourly()).willReturn(openWeatherHourlyDtos);

        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)).willReturn(openWeatherOneCallDto);

        City properAnswer = CITY;

        // when
        weatherClient.getHourlyWeatherForecastData(CITY);

        // then
        assertThat(weatherClient.getLastCheckedCity()).isEqualTo(properAnswer);
    }

    @Tag("MyLogic")
    @Test
    void whenNoExceptionOccurredGetWeeklyWeatherForecastDataShouldReturnProperWeatherDto() throws JsonProcessingException, ApiException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(float.class), any(float.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);

        OpenWeatherOneCallDto openWeatherOneCallDto = mock(OpenWeatherOneCallDto.class);
        OpenWeatherDailyDto[] openWeatherDailyDtos = getArrayOfOpenWeatherDailyDto();
        given(openWeatherOneCallDto.getDaily()).willReturn(openWeatherDailyDtos);

        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)).willReturn(openWeatherOneCallDto);

        // when
        List<WeeklyForecastDto> response = weatherClient.getWeeklyWeatherForecastData(CITY);

        // then
        assertThat(response.get(0).getDateTime()).isEqualTo(OpenWeatherDailyDtoStaticData.UNIX_DATE_TIME_1);
        assertThat(response.get(0).getDayTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.DAY_TEMP_INT_1);
        assertThat(response.get(0).getNightTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.NIGHT_TEMP_INT_1);
        assertThat(response.get(0).getMinTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.MIN_TEMP_INT_1);
        assertThat(response.get(0).getMaxTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.MAX_TEMP_INT_1);
        assertThat(response.get(0).getDayFeelsLike()).isEqualTo(OpenWeatherDailyDtoStaticData.DAY_FEELS_LIKE_INT_1);
        assertThat(response.get(0).getNightFeelsLike()).isEqualTo(OpenWeatherDailyDtoStaticData.NIGHT_FEELS_LIKE_INT_1);
        assertThat(response.get(0).getPressure()).isEqualTo(OpenWeatherDailyDtoStaticData.PRESSURE_1);
        assertThat(response.get(0).getHumidity()).isEqualTo(OpenWeatherDailyDtoStaticData.HUMIDITY_1);
        assertThat(response.get(0).getWindSpeed()).isEqualTo(OpenWeatherDailyDtoStaticData.WIND_SPEED_KMPS_1);
        assertThat(response.get(0).getDescription()).isEqualTo(OpenWeatherDailyDtoStaticData.DESCRIPTION_1);
        assertThat(response.get(0).getIcon()).isEqualTo(OpenWeatherDailyDtoStaticData.ICON_1);

        assertThat(response.get(1).getDateTime()).isEqualTo(OpenWeatherDailyDtoStaticData.UNIX_DATE_TIME_2);
        assertThat(response.get(1).getDayTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.DAY_TEMP_INT_2);
        assertThat(response.get(1).getNightTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.NIGHT_TEMP_INT_2);
        assertThat(response.get(1).getMinTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.MIN_TEMP_INT_2);
        assertThat(response.get(1).getMaxTemperature()).isEqualTo(OpenWeatherDailyDtoStaticData.MAX_TEMP_INT_2);
        assertThat(response.get(1).getDayFeelsLike()).isEqualTo(OpenWeatherDailyDtoStaticData.DAY_FEELS_LIKE_INT_2);
        assertThat(response.get(1).getNightFeelsLike()).isEqualTo(OpenWeatherDailyDtoStaticData.NIGHT_FEELS_LIKE_INT_2);
        assertThat(response.get(1).getPressure()).isEqualTo(OpenWeatherDailyDtoStaticData.PRESSURE_2);
        assertThat(response.get(1).getHumidity()).isEqualTo(OpenWeatherDailyDtoStaticData.HUMIDITY_2);
        assertThat(response.get(1).getWindSpeed()).isEqualTo(OpenWeatherDailyDtoStaticData.WIND_SPEED_KMPS_2);
        assertThat(response.get(1).getDescription()).isEqualTo(OpenWeatherDailyDtoStaticData.DESCRIPTION_2);
        assertThat(response.get(1   ).getIcon()).isEqualTo(OpenWeatherDailyDtoStaticData.ICON_2);
    }

    private OpenWeatherDailyDto[] getArrayOfOpenWeatherDailyDto() {

        OpenWeatherDailyDto openWeatherDailyDto1 = mock(OpenWeatherDailyDto.class);
        given(openWeatherDailyDto1.getDt()).willReturn(OpenWeatherDailyDtoStaticData.UNIX_DATE_TIME_1);
        given(openWeatherDailyDto1.getDayTemp()).willReturn(OpenWeatherDailyDtoStaticData.DAY_TEMP_FLOAT_1);
        given(openWeatherDailyDto1.getNightTemp()).willReturn(OpenWeatherDailyDtoStaticData.NIGHT_TEMP_FLOAT_1);
        given(openWeatherDailyDto1.getMinTemp()).willReturn(OpenWeatherDailyDtoStaticData.MIN_TEMP_FLOAT_1);
        given(openWeatherDailyDto1.getMaxTemp()).willReturn(OpenWeatherDailyDtoStaticData.MAX_TEMP_FLOAT_1);
        given(openWeatherDailyDto1.getDayFeelsLike()).willReturn(OpenWeatherDailyDtoStaticData.DAY_FEELS_LIKE_FLOAT_1);
        given(openWeatherDailyDto1.getNightFeelsLike()).willReturn(OpenWeatherDailyDtoStaticData.NIGHT_FEELS_LIKE_FLOAT_1);
        given(openWeatherDailyDto1.getPressure()).willReturn(OpenWeatherDailyDtoStaticData.PRESSURE_1);
        given(openWeatherDailyDto1.getHumidity()).willReturn(OpenWeatherDailyDtoStaticData.HUMIDITY_1);
        given(openWeatherDailyDto1.getWindSpeed()).willReturn(OpenWeatherDailyDtoStaticData.WIND_SPEED_MPS_1);
        given(openWeatherDailyDto1.getDescription()).willReturn(OpenWeatherDailyDtoStaticData.DESCRIPTION_1);
        given(openWeatherDailyDto1.getIcon()).willReturn(OpenWeatherDailyDtoStaticData.ICON_1);

        OpenWeatherDailyDto openWeatherDailyDto2 = mock(OpenWeatherDailyDto.class);
        given(openWeatherDailyDto2.getDt()).willReturn(OpenWeatherDailyDtoStaticData.UNIX_DATE_TIME_2);
        given(openWeatherDailyDto2.getDayTemp()).willReturn(OpenWeatherDailyDtoStaticData.DAY_TEMP_FLOAT_2);
        given(openWeatherDailyDto2.getNightTemp()).willReturn(OpenWeatherDailyDtoStaticData.NIGHT_TEMP_FLOAT_2);
        given(openWeatherDailyDto2.getMinTemp()).willReturn(OpenWeatherDailyDtoStaticData.MIN_TEMP_FLOAT_2);
        given(openWeatherDailyDto2.getMaxTemp()).willReturn(OpenWeatherDailyDtoStaticData.MAX_TEMP_FLOAT_2);
        given(openWeatherDailyDto2.getDayFeelsLike()).willReturn(OpenWeatherDailyDtoStaticData.DAY_FEELS_LIKE_FLOAT_2);
        given(openWeatherDailyDto2.getNightFeelsLike()).willReturn(OpenWeatherDailyDtoStaticData.NIGHT_FEELS_LIKE_FLOAT_2);
        given(openWeatherDailyDto2.getPressure()).willReturn(OpenWeatherDailyDtoStaticData.PRESSURE_2);
        given(openWeatherDailyDto2.getHumidity()).willReturn(OpenWeatherDailyDtoStaticData.HUMIDITY_2);
        given(openWeatherDailyDto2.getWindSpeed()).willReturn(OpenWeatherDailyDtoStaticData.WIND_SPEED_MPS_2);
        given(openWeatherDailyDto2.getDescription()).willReturn(OpenWeatherDailyDtoStaticData.DESCRIPTION_2);
        given(openWeatherDailyDto2.getIcon()).willReturn(OpenWeatherDailyDtoStaticData.ICON_2);

        return new OpenWeatherDailyDto[]{openWeatherDailyDto1, openWeatherDailyDto2};
    }

    @Tag("MyLogic")
    @Test
    void whenJsonProcessingExceptionIsThrownInGetWeeklyWeatherForecastDataApiExceptionShouldBeThrown() throws JsonProcessingException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(float.class), any(float.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)).willThrow(JsonProcessingException.class);

        // when
        // then
        assertThrows(ApiException.class, () ->  weatherClient.getWeeklyWeatherForecastData(CITY));
    }

    @Tag("MyLogic")
    @Test
    void afterGetWeeklyWeatherForecastDataLastCheckedCityFieldShouldBeExactlyForWhichYouCheckedWeather() throws ApiException, JsonProcessingException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(float.class), any(float.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);

        OpenWeatherOneCallDto openWeatherOneCallDto = mock(OpenWeatherOneCallDto.class);
        OpenWeatherDailyDto[] openWeatherDailyDtos = getArrayOfOpenWeatherDailyDto();
        given(openWeatherOneCallDto.getDaily()).willReturn(openWeatherDailyDtos);

        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherOneCallDto.class)).willReturn(openWeatherOneCallDto);

        City properAnswer = CITY;

        // when
        weatherClient.getWeeklyWeatherForecastData(CITY);

        // then
        assertThat(weatherClient.getLastCheckedCity()).isEqualTo(properAnswer);
    }

    @Tag("MyLogic")
    @Test
    void whenNoExceptionOccurredGetCityInfoShouldReturnProperCityObj() throws JsonProcessingException, ApiException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(String.class), any(String.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        OpenWeatherGeocodingCityDto[] openWeatherDailyDtos = getArrayOfOpenWeatherGeocodingCityDto();
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherGeocodingCityDto[].class)).willReturn(openWeatherDailyDtos);

        City properAnswer = new SpecificCity("Name", 20F, 30F);

        // when
        City response = weatherClient.getCityInfo("Name", "ISOCode");

        // then
        assertThat(response).isEqualTo(properAnswer);
    }

    private OpenWeatherGeocodingCityDto[] getArrayOfOpenWeatherGeocodingCityDto() {
        OpenWeatherGeocodingCityDto openWeatherGeocodingCityDto = mock(OpenWeatherGeocodingCityDto.class);
        given(openWeatherGeocodingCityDto.getLat()).willReturn(20F);
        given(openWeatherGeocodingCityDto.getLon()).willReturn(30F);

        return new OpenWeatherGeocodingCityDto[]{openWeatherGeocodingCityDto};
    }

    @Tag("MyLogic")
    @Test
    void whenJsonProcessingExceptionIsThrownInGetCityInfoApiExceptionShouldBeThrown() throws JsonProcessingException, ApiException {
        // given
        given(restTemplate.getForObject(any(String.class), any(Class.class), any(String.class), any(String.class), any(String.class))).willReturn(StaticData.PROPER_JSON_STRING);
        given(objectMapper.readValue(StaticData.PROPER_JSON_STRING, OpenWeatherGeocodingCityDto[].class)).willThrow(JsonProcessingException.class);

        // when
        // then
        assertThrows(ApiException.class, () ->  weatherClient.getCityInfo("Name", "ISOCode"));
    }
}