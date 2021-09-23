package pl.adrian_komuda.controllers;

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
import pl.adrian_komuda.HelpingMethods;
import pl.adrian_komuda.model.*;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.*;

public class WeatherViewController implements Initializable {
    private final WeatherClient weatherClient = new WeatherClient();
    private ObservableList<String> countriesList = FXCollections.observableArrayList();
    private final Map<String, ObservableList<City>> countiesCitiesMap = CustomLocales.getCountriesCitiesMap();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpCities();
        setUpChangeCityChoiceBoxListener();
        setUpChangeCountryChoiceBoxListener();
        //showLastHourlyWeather();
    }

    private void setUpCities() {
        // Getting countries' list
        countriesList.addAll(countiesCitiesMap.keySet());
        countriesList = countriesList.sorted();

        // Adding to choice boxes
        countryChoiceBox.setItems(countriesList);
    }

    private void showCurrentWeatherForCity(String city) {
        WeatherDto currentWeatherForCity = weatherClient.getCurrentWeatherData(city);
        temperatureValueLabel.setText(currentWeatherForCity.getTemperature() + " " + currentWeatherForCity.getTemperatureUnit());
        pressureValueLabel.setText(currentWeatherForCity.getPressure() + " " + currentWeatherForCity.getPressureUnit());
        humidityValueLabel.setText(currentWeatherForCity.getHumidity() + " " + currentWeatherForCity.getHumidityUnit());
        windSpeedValueLabel.setText(currentWeatherForCity.getWindSpeed() + " " + currentWeatherForCity.getWindSpeedUnit());
    }

    private void setUpChangeCityChoiceBoxListener() {
        cityChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                if (newIndex.intValue() < 0) return;

                String activeCountry = countryChoiceBox.getValue();
                String newCityName = countiesCitiesMap.get(activeCountry).get(newIndex.intValue()).getName();

                showCurrentWeatherForCity(newCityName);
                showHourlyWeather(newCityName);
            }
        });
    }

    private void setUpChangeCountryChoiceBoxListener() {
        countryChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                String newCountryName = countriesList.get(newIndex.intValue());
                ObservableList<City> newCitiesList = countiesCitiesMap.get(newCountryName);
                //newCitiesList = newCitiesList.sorted();

                cityChoiceBox.setItems(newCitiesList);
            }
        });
    }

    private void showLastHourlyWeather() {
        showHourlyWeather(null);
    }

    private void showHourlyWeather(String cityName) {
        List<HourlyWeatherDto> hourlyWeatherDtos;

        if (cityName != null) {
            City cityObj = findCityByName(cityName);
            hourlyWeatherDtos = weatherClient.getHourlyWeatherForecastData(cityObj.getLatitude(), cityObj.getLongitude());
        }
        else {
            hourlyWeatherDtos = weatherClient.getLastHourlyWeatherForecastData();
            System.out.println("hourlyWeatherDtos: " + hourlyWeatherDtos);
            if (hourlyWeatherDtos == null) return;
        }

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
            VBox gridViewHour = new VBox(new Label(hour));

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

    private City findCityByName(String cityName) {
        String activeCountry = countryChoiceBox.getValue();
        ObservableList<City> activeCitiesList = countiesCitiesMap.get(activeCountry);
        for (City cityObj : activeCitiesList) {
            if (cityName.equals(cityObj.getName())) return cityObj;
        }
        return null;
    }
}