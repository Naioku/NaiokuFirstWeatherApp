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
import pl.adrian_komuda.model.City;
import pl.adrian_komuda.model.HourlyWeatherDto;
import pl.adrian_komuda.model.WeatherDto;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WeatherViewController implements Initializable {
    private final WeatherClient weatherClient = new WeatherClient();
    private final ObservableList<City> citiesList = FXCollections.observableArrayList();

    @FXML
    private Label headerLabel;

    @FXML
    private ChoiceBox<?> countryChoiceBox;

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
        setUpHomeCities();
        showCurrentWeatherForCity(cityChoiceBox.getValue().getName());
        showHourlyWeather(cityChoiceBox.getValue().getName());
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
        cityChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                String newCityName = citiesList.get(newIndex.intValue()).getName();

                showCurrentWeatherForCity(newCityName);
                showHourlyWeather(newCityName);
            }
        });
    }

    private void setUpHomeCities() {
        citiesList.add(weatherClient.getCityInfo("Plock"));
        citiesList.add(weatherClient.getCityInfo("Warsaw"));
        citiesList.add(weatherClient.getCityInfo("Tokyo"));
        citiesList.add(weatherClient.getCityInfo("London"));

        cityChoiceBox.setItems(citiesList);
        cityChoiceBox.setValue(citiesList.get(0));
    }

    private void showHourlyWeather(String cityName) {
        City cityObj = findCityByName(cityName);
        List<HourlyWeatherDto> hourlyWeatherDtos = weatherClient.getHourlyWeatherForOneCords(cityObj.getLatitude(), cityObj.getLongitude());

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
        hourlyWeatherChart.getData().addAll(temperatureSeries, uviSeries);
    }

    private City findCityByName(String cityName) {
        for (City cityObj : citiesList) {
            if (cityName.equals(cityObj.getName())) return cityObj;
        }
        return null;
    }
}
