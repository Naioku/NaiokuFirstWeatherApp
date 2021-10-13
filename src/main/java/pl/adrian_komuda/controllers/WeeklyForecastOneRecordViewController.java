package pl.adrian_komuda.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pl.adrian_komuda.utilities.HelpingMethods;
import pl.adrian_komuda.weather_client.my_dtos.WeeklyForecastDto;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.ResourceBundle;

public class WeeklyForecastOneRecordViewController extends BaseController implements Initializable {
    private final WeeklyForecastDto weeklyForecastDto;
    private final WeatherClient weatherClient;

    @FXML
    private Label tempDayValue;

    @FXML
    private Label tempNightValue;

    @FXML
    private Label tempMinValue;

    @FXML
    private Label tempMaxValue;

    @FXML
    private Label feelsLikeDayValue;

    @FXML
    private Label feelsLikeNightValue;

    @FXML
    private Label pressureValue;

    @FXML
    private Label humidityValue;

    @FXML
    private Label windSpeedValue;

    @FXML
    private VBox iconVBox;

    @FXML
    private Label descriptionValue;

    @FXML
    private Label dateValue;

    public WeeklyForecastOneRecordViewController(String fxmlName, WeeklyForecastDto weeklyForecastDto, WeatherClient weatherClient) {
        super(fxmlName);
        this.weeklyForecastDto = weeklyForecastDto;
        this.weatherClient = weatherClient;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpValues();
    }

    private void setUpValues() {
        dateValue.setText(HelpingMethods.convertUNIXSecondsToDate(weeklyForecastDto.getDateTime()));
        tempDayValue.setText(weeklyForecastDto.getDayTemperature() + " " + weeklyForecastDto.getTemperatureUnit());
        tempNightValue.setText(weeklyForecastDto.getNightTemperature() + " " + weeklyForecastDto.getTemperatureUnit());
        tempMinValue.setText(weeklyForecastDto.getMinTemperature() + " " + weeklyForecastDto.getTemperatureUnit());
        tempMaxValue.setText(weeklyForecastDto.getMaxTemperature() + " " + weeklyForecastDto.getTemperatureUnit());
        feelsLikeDayValue.setText(weeklyForecastDto.getDayFeelsLike() + " " + weeklyForecastDto.getTemperatureUnit());
        feelsLikeNightValue.setText(weeklyForecastDto.getNightFeelsLike() + " " + weeklyForecastDto.getTemperatureUnit());
        pressureValue.setText(weeklyForecastDto.getPressure() + " " + weeklyForecastDto.getPressureUnit());
        humidityValue.setText(weeklyForecastDto.getHumidity() + " " + weeklyForecastDto.getHumidityUnit());
        windSpeedValue.setText(weeklyForecastDto.getWindSpeed() + " " + weeklyForecastDto.getWindSpeedUnit());
        setIcon();
        descriptionValue.setText(weeklyForecastDto.getDescription());
    }

    private void setIcon() {
        ImageView iconImage = weatherClient.getWeatherIcon(weeklyForecastDto.getIcon());
        iconImage.setFitWidth(50D);
        iconImage.setPreserveRatio(true);
        iconVBox.getChildren().clear();
        iconVBox.getChildren().add(iconImage);
    }
}
