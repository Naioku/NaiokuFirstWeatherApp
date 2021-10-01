package pl.adrian_komuda.HelpingClasses;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import pl.adrian_komuda.Model.City;
import pl.adrian_komuda.Model.CustomLocales;
import pl.adrian_komuda.weather_client.WeatherClient;

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
