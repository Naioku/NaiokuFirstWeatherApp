package pl.adrian_komuda.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import pl.adrian_komuda.views.ViewFactory;

public class MainViewController {

    @FXML
    public void mainMenuCloseAction() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void mainMenuAboutAction() {
        ViewFactory.showAbout();
    }

    @FXML
    void leftPanelButtonAddCityOnAction() {
        ViewFactory.switchCenterViewToAddCityView();
    }

    @FXML
    void leftPanelButtonDeleteCityOnAction() {
        ViewFactory.switchCenterViewToDeleteCityView();
    }

    @FXML
    void leftPanelButtonWeatherOnAction() {
        ViewFactory.switchCenterViewToWeatherView();
    }

    @FXML
    void leftPanelButtonOptionsOnAction() {
        ViewFactory.switchCenterViewToOptions();
    }
}
