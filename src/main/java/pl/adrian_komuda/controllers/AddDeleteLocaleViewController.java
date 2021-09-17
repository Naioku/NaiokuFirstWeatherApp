package pl.adrian_komuda.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import pl.adrian_komuda.HelpingClasses.ConvertingLocales;
import pl.adrian_komuda.HelpingClasses.CustomExceptions.NoSuchItemInMapException;
import pl.adrian_komuda.model.City;
import pl.adrian_komuda.model.CustomLocales;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddDeleteLocaleViewController implements Initializable {
    CustomLocales customLocales;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label errorLabel;

    @FXML
    void addLocaleAction() {
        resetErrorLabel();

        String countryName = countryTextField.getText();
        String cityName = cityTextField.getText();

        Locale.setDefault(new Locale("en"));
        ConvertingLocales convertingLocales = new ConvertingLocales();

        String countryISO;
        try {
            countryISO = convertingLocales.convertNameToISO(countryName);
        } catch (NoSuchItemInMapException e) {
            errorLabel.setText("You have probably made typo in adding new locale or entered names in language different from what You have been set.");
            e.printStackTrace();
            return;
        }

        WeatherClient weatherClient = new WeatherClient();
        City cityObj = weatherClient.getCityInfo(cityName, countryISO);

        customLocales.addLocale(countryName, cityObj);

//        customLocales.printAllSavedLocales(); // HERE


    }

    @FXML
    void deleteLocaleAction() {
        resetErrorLabel();
        customLocales.deleteLocale();
        customLocales.printAllSavedLocales();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customLocales = new CustomLocales(treeView);
    }


    private void resetErrorLabel() {
        errorLabel.setText("FINE");
    }


}
