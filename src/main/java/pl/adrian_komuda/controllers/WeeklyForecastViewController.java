package pl.adrian_komuda.controllers;

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
import pl.adrian_komuda.utilities.ChoiceBoxesHandler;
import pl.adrian_komuda.utilities.ErrorDialogsContent;
import pl.adrian_komuda.weather_client.my_dtos.City;
import pl.adrian_komuda.model.CustomLocations;
import pl.adrian_komuda.weather_client.my_dtos.WeeklyForecastDto;
import pl.adrian_komuda.utilities.ErrorMessages;
import pl.adrian_komuda.utilities.custom_exceptions.ApiException;
import pl.adrian_komuda.views.ViewFactory;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class WeeklyForecastViewController extends BaseController implements Initializable {
    private final WeatherClient weatherClient;
    private final ObservableList<String> countriesList = FXCollections.observableArrayList();
    private final Map<String, ObservableList<City>> countiesCitiesMap = CustomLocations.getCountriesCitiesMap();
    private ChoiceBoxesHandler choiceBoxesHandler;

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

    @FXML
    private Label errorLabel;

    public WeeklyForecastViewController(String fxmlName, WeatherClient weatherClient) {
        super(fxmlName);
        this.weatherClient = weatherClient;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxesHandler = new ChoiceBoxesHandler(
                weatherClient,
                countriesList,
                countiesCitiesMap,
                countryChoiceBox,
                cityChoiceBox,
                cityChoiceBoxListener,
                countryChoiceBoxListener
        );
    }

    private final ChangeListener<Number> cityChoiceBoxListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
            if (newIndex.intValue() < 0) return;

            String activeCountry = countryChoiceBox.getValue();
            City newCity = countiesCitiesMap.get(activeCountry).get(newIndex.intValue());

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
        List<WeeklyForecastDto> weeklyForecastDtoList;

        try {
            weeklyForecastDtoList = weatherClient.getWeeklyWeatherForecastData(city);
        } catch (ApiException e) {
            e.printStackTrace();
            errorLabel.setText(ErrorMessages.WEATHER_API_COULD_NOT_LOAD_DATA);
            ViewFactory.throwErrorDialog(ErrorDialogsContent.GENERAL, e);
            return;
        }

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
