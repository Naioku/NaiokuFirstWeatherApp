package pl.adrian_komuda.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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
    void leftPanelButtonAddDeleteLocaleOnAction() {
        ViewFactory.switchCenterViewToAddDeleteLocaleView();
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
