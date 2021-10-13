package pl.adrian_komuda.utilities;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import pl.adrian_komuda.weather_client.my_dtos.City;
import pl.adrian_komuda.model.CustomLocations;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.util.Map;

public class ChoiceBoxesHandler {
    private final WeatherClient weatherClient;
    private ObservableList<String> countriesList;
    private final Map<String, ObservableList<City>> countiesCitiesMap;

    private final ChoiceBox<String> countryChoiceBox;
    private final ChoiceBox<City> cityChoiceBox;

    public ChoiceBoxesHandler(
            WeatherClient weatherClient,
            ObservableList<String> countriesList,
            Map<String, ObservableList<City>> countiesCitiesMap,
            ChoiceBox<String> countryChoiceBox,
            ChoiceBox<City> cityChoiceBox,
            ChangeListener<Number> cityChoiceBoxListener,
            ChangeListener<Number> countryChoiceBoxListener
    ){
        this.weatherClient = weatherClient;
        this.countriesList = countriesList;
        this.countiesCitiesMap = countiesCitiesMap;
        this.countryChoiceBox = countryChoiceBox;
        this.cityChoiceBox = cityChoiceBox;
        setUpCities();
        setUpChangeCityChoiceBoxListener(cityChoiceBoxListener);
        setUpChangeCountryChoiceBoxListener(countryChoiceBoxListener);
        setLastCheckedCountry();
        setLastCheckedCity();
    }

    private void setLastCheckedCountry() {
        City lastCheckedCity = weatherClient.getLastCheckedCity();
        if (lastCheckedCity != null) {
            countryChoiceBox.setValue(CustomLocations.findCountryByCity(lastCheckedCity));
        }
    }

    private void setLastCheckedCity() {
        City lastCheckedCity = weatherClient.getLastCheckedCity();
        if (lastCheckedCity != null) {
            cityChoiceBox.setValue(lastCheckedCity);
        }
    }

    private void setUpCities() {
        // Getting countries' list
        countriesList.addAll(countiesCitiesMap.keySet());
        countriesList = countriesList.sorted();

        // Adding to choice boxes
        countryChoiceBox.setItems(countriesList);
    }

    private void setUpChangeCityChoiceBoxListener(ChangeListener<Number> listener) {
        cityChoiceBox.getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    private void setUpChangeCountryChoiceBoxListener(ChangeListener<Number> listener) {
        countryChoiceBox.getSelectionModel().selectedIndexProperty().addListener(listener);
    }
}
