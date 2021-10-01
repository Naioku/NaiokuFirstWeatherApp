package pl.adrian_komuda.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.paint.Paint;
import pl.adrian_komuda.HelpingClasses.ChoiceBoxesHandling;
import pl.adrian_komuda.HelpingMethods;
import pl.adrian_komuda.Model.*;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.*;

public class WeatherViewController extends BaseController implements Initializable {
    private final WeatherClient weatherClient;
    private ObservableList<String> countriesList = FXCollections.observableArrayList();
    private final Map<String, ObservableList<City>> countiesCitiesMap = CustomLocales.getCountriesCitiesMap();
    private ChoiceBoxesHandling choiceBoxesHandlingObject;

    @FXML
    private Label headerLabel;

    @FXML
    private ChoiceBox<String> countryChoiceBox;

    @FXML
    private ChoiceBox<City> cityChoiceBox;

    @FXML
    private Label temperatureValueLabel;

    @FXML
    private Label pressureValueLabel;

    @FXML
    private Label humidityValueLabel;

    @FXML
    private Label windSpeedValueLabel;

    @FXML
    private BarChart<?, ?> hourlyWeatherChart;

    @FXML
    private VBox hourlyWeatherGridPaneVbox;

    private final ChangeListener<Number> cityChoiceBoxListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
            if (newIndex.intValue() < 0) return;

            String activeCountry = countryChoiceBox.getValue();
            City newCity = countiesCitiesMap.get(activeCountry).get(newIndex.intValue());

            showCurrentWeatherForCity(newCity);
            showHourlyWeather(newCity);
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

    public WeatherViewController(String fxmlName, WeatherClient weatherClient) {
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
    }

    private void showCurrentWeatherForCity(City city) {
        WeatherDto currentWeatherForCity = weatherClient.getCurrentWeatherData(city);
        temperatureValueLabel.setText(currentWeatherForCity.getTemperature() + " " + currentWeatherForCity.getTemperatureUnit());
        pressureValueLabel.setText(currentWeatherForCity.getPressure() + " " + currentWeatherForCity.getPressureUnit());
        humidityValueLabel.setText(currentWeatherForCity.getHumidity() + " " + currentWeatherForCity.getHumidityUnit());
        windSpeedValueLabel.setText(currentWeatherForCity.getWindSpeed() + " " + currentWeatherForCity.getWindSpeedUnit());
    }

    private void showHourlyWeather(City cityObj) {
        List<HourlyWeatherDto> hourlyWeatherDtos;
        hourlyWeatherDtos = weatherClient.getHourlyWeatherForecastData(cityObj);

        int quantityOfHourlyRecords = 24;
        createHourlyWeatherTableView(hourlyWeatherDtos, quantityOfHourlyRecords);
        createHourlyWeatherChart(hourlyWeatherDtos, quantityOfHourlyRecords);
    }

    private void createHourlyWeatherTableView(List<HourlyWeatherDto> hourlyWeatherDtos, int quantityOfHourlyRecords) {
        GridPane hourlyWeatherGridPane = new GridPane();
        int columnIndex = 0;
        while (columnIndex < quantityOfHourlyRecords) {
            // Getting data from weather dto
            HourlyWeatherDto hourlyWeatherDto = hourlyWeatherDtos.get(columnIndex);
            String hour = HelpingMethods.convertUNIXSecondsToHourString(hourlyWeatherDto.getDateTime());
            String iconCode = hourlyWeatherDto.getIcon();
            String description = hourlyWeatherDto.getDescription();

            // Creating the grid elements
            Label hourLabel = new Label(hour);
            hourLabel.getStyleClass().add("record-header");
            VBox gridViewHour = new VBox(hourLabel);

            ImageView iconImage = weatherClient.getWeatherIcon(iconCode);
            iconImage.setFitWidth(50D);
            iconImage.setPreserveRatio(true);
            VBox gridViewIcon = new VBox(iconImage);

            VBox gridViewDescription = new VBox(new Label(description));

            // Adding grid elements to the list
            List<VBox> gridElements = new ArrayList<>();
            gridElements.add(gridViewHour);
            gridElements.add(gridViewIcon);
            gridElements.add(gridViewDescription);

            setTableViewPadding(gridElements);
            setTableViewContentCentered(gridElements);
            addElementsToTheGridPane(hourlyWeatherGridPane, gridElements, columnIndex);
            columnIndex++;

            gridViewIcon.setBackground(new Background(new BackgroundFill(Paint.valueOf("#c7c7c7"), CornerRadii.EMPTY, Insets.EMPTY))); // HERE
        }
        hourlyWeatherGridPaneVbox.getChildren().clear();
        hourlyWeatherGridPaneVbox.getChildren().add(hourlyWeatherGridPane);
    }

    private void setTableViewContentCentered(List<VBox> elements) {
        for (VBox element : elements) {
            element.setPadding(new Insets(5D, 10D, 5D, 10D));
        }
    }

    private void setTableViewPadding(List<VBox> elements) {
        for (VBox element : elements) {
            element.setFillWidth(true);
            element.setAlignment(Pos.CENTER);
        }
    }

    private void addElementsToTheGridPane(GridPane gridPane, List<VBox> elements, int columnIndex) {
        int rowIndex = 0;
        for (VBox element : elements) {
            gridPane.add(element, columnIndex, rowIndex);
            rowIndex++;
        }
    }

    private void createHourlyWeatherChart(List<HourlyWeatherDto> hourlyWeatherDtos, int quantityOfHourlyRecords) {
        XYChart.Series temperatureSeries = new XYChart.Series();
        XYChart.Series uviSeries = new XYChart.Series();
        temperatureSeries.setName("Temperature");
        uviSeries.setName("UVI");
        hourlyWeatherChart.getYAxis().setLabel("°C/uvi");

        int i = 0;
        while (i < quantityOfHourlyRecords) {
            int temperature = hourlyWeatherDtos.get(i).getTemperature();
            int uvi = hourlyWeatherDtos.get(i).getUvi();
            String hour = HelpingMethods.convertUNIXSecondsToHourString(hourlyWeatherDtos.get(i).getDateTime());
            temperatureSeries.getData().add(new XYChart.Data(hour, temperature));
            uviSeries.getData().add(new XYChart.Data(hour, uvi));
            i++;
        }
        hourlyWeatherChart.getData().clear();
        hourlyWeatherChart.layout();
        hourlyWeatherChart.getData().addAll(temperatureSeries, uviSeries);
    }
}
