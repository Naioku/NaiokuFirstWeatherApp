package pl.adrian_komuda.HelpingClasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import pl.adrian_komuda.HelpingMethods;
import pl.adrian_komuda.model.City;
import pl.adrian_komuda.model.CustomLocales;
import pl.adrian_komuda.model.HourlyWeatherDto;
import pl.adrian_komuda.model.WeatherDto;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChoiceBoxesHandling {
    private final WeatherClient weatherClient;
    private ObservableList<String> countriesList;
    private final Map<String, ObservableList<City>> countiesCitiesMap;

    private final ChoiceBox<String> countryChoiceBox;
    private final ChoiceBox<City> cityChoiceBox;

    public ChoiceBoxesHandling(
            WeatherClient weatherClient,
            ObservableList<String> countriesList,
            Map<String, ObservableList<City>> countiesCitiesMap,
            ChoiceBox<String> countryChoiceBox,
            ChoiceBox<City> cityChoiceBox
    ){
        this.weatherClient = weatherClient;
        this.countriesList = countriesList;
        this.countiesCitiesMap = countiesCitiesMap;
        this.countryChoiceBox = countryChoiceBox;
        this.cityChoiceBox = cityChoiceBox;
    }

    public void setLastCheckedCountry() {
        City lastCheckedCity = weatherClient.getLastCheckedCity();
        if (lastCheckedCity != null) {
            countryChoiceBox.setValue(CustomLocales.findCountryByCity(lastCheckedCity));
        }
    }

    public void setLastCheckedCity() {
        City lastCheckedCity = weatherClient.getLastCheckedCity();
        if (lastCheckedCity != null) {
            cityChoiceBox.setValue(lastCheckedCity);
        }
    }

    public void setUpCities() {
        // Getting countries' list
        countriesList.addAll(countiesCitiesMap.keySet());
        countriesList = countriesList.sorted();

        // Adding to choice boxes
        countryChoiceBox.setItems(countriesList);
    }

    public void setUpChangeCityChoiceBoxListener(ChangeListener<Number> listener) {
        cityChoiceBox.getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    public void setUpChangeCountryChoiceBoxListener(ChangeListener<Number> listener) {
        countryChoiceBox.getSelectionModel().selectedIndexProperty().addListener(listener);
    }
}
