package pl.adrian_komuda.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.adrian_komuda.HelpingClasses.ChoiceBoxesHandling;
import pl.adrian_komuda.Model.City;
import pl.adrian_komuda.Model.CustomLocales;
import pl.adrian_komuda.Model.WeeklyForecastDto;
import pl.adrian_komuda.Views.ViewFactory;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class WeeklyForecastViewController extends BaseController implements Initializable {
    private WeatherClient weatherClient;
    private ObservableList<String> countriesList = FXCollections.observableArrayList();
    private final Map<String, ObservableList<City>> countiesCitiesMap = CustomLocales.getCountriesCitiesMap();
    private ChoiceBoxesHandling choiceBoxesHandlingObject;

    @FXML
    private VBox headerVBox;

    @FXML
    private Label headerLabel;

    @FXML
    private ChoiceBox<String> countryChoiceBox;

    @FXML
    private ChoiceBox<City> cityChoiceBox;

    @FXML
    private VBox recordsVBox;

    public WeeklyForecastViewController(String fxmlName, WeatherClient weatherClient) {
        super(fxmlName);
        this.weatherClient = weatherClient;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxesHandlingObject = new ChoiceBoxesHandling(
                weatherClient,
                countriesList,
                countiesCitiesMap,
                countryChoiceBox,
                cityChoiceBox
        );
        choiceBoxesHandlingObject.setUpCities();
        choiceBoxesHandlingObject.setUpChangeCityChoiceBoxListener(cityChoiceBoxListener);
        choiceBoxesHandlingObject.setUpChangeCountryChoiceBoxListener(countryChoiceBoxListener);
        choiceBoxesHandlingObject.setLastCheckedCountry();
        choiceBoxesHandlingObject.setLastCheckedCity();
//        showWeeklyForecast();
    }

    private final ChangeListener<Number> cityChoiceBoxListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
            if (newIndex.intValue() < 0) return;

            String activeCountry = countryChoiceBox.getValue();
            City newCity = countiesCitiesMap.get(activeCountry).get(newIndex.intValue());

            // show weekly forecast records
            showWeeklyForecast(newCity);
        }
    };

    private final ChangeListener<Number> countryChoiceBoxListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
            String newCountryName = countriesList.get(newIndex.intValue());
            ObservableList<City> newCitiesList = countiesCitiesMap.get(newCountryName);

            cityChoiceBox.setItems(newCitiesList);
        }
    };

    private void showWeeklyForecast(City city) {
        List<WeeklyForecastDto> weeklyForecastDtoList = weatherClient.getWeeklyWeatherForecastData(city);

        recordsVBox.getChildren().clear();
        for (WeeklyForecastDto weeklyForecastDto : weeklyForecastDtoList) {
            Parent parent = ViewFactory.loadFXML(new WeeklyForecastOneRecordViewController(
                    "WeeklyForecastOneRecordView",
                    weeklyForecastDto,
                    weatherClient
            ));
            recordsVBox.getChildren().add(parent);
        }
    }
}
