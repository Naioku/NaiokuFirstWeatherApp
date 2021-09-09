package pl.adrian_komuda.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import pl.adrian_komuda.model.WeatherDto;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.ResourceBundle;

public class WeatherViewController implements Initializable {
    private final WeatherClient weatherClient = new WeatherClient();
    private final ObservableList<String> citiesList = FXCollections.observableArrayList("Plock", "Warsaw", "Tokyo", "London");

    @FXML
    private ChoiceBox<?> homeCountry;

    @FXML
    private ChoiceBox<String> homeCity;

    @FXML
    private ChoiceBox<?> chosenCountry;

    @FXML
    private ChoiceBox<?> chosenCity;

    @FXML
    private Label temperatureValueLabel;

    @FXML
    private Label pressureValueLabel;

    @FXML
    private Label humidityValueLabel;

    @FXML
    private Label windSpeedValueLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpHomeCities();
        showCurrentWeatherForCity(homeCity.getValue());
        setUpChangeHomeCityChoiceBoxListener();
    }

    private void showCurrentWeatherForCity(String city) {
        WeatherDto currentWeatherForCity = weatherClient.getCurrentWeatherForCity(city);
        temperatureValueLabel.setText(currentWeatherForCity.getTemperature() + " " + currentWeatherForCity.getTemperatureUnit());
        pressureValueLabel.setText(currentWeatherForCity.getPressure() + " " + currentWeatherForCity.getPressureUnit());
        humidityValueLabel.setText(currentWeatherForCity.getHumidity() + " " + currentWeatherForCity.getHumidityUnit());
        windSpeedValueLabel.setText(currentWeatherForCity.getWindSpeed() + " " + currentWeatherForCity.getWindSpeedUnit());
    }

    private void setUpChangeHomeCityChoiceBoxListener() {
        homeCity.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                showCurrentWeatherForCity(citiesList.get(newIndex.intValue()));
            }
        });
    }

    private void setUpHomeCities() {
        homeCity.setItems(citiesList);
        homeCity.setValue(citiesList.get(0));
    }


}
