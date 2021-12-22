package pl.adrian_komuda.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import pl.adrian_komuda.views.ViewFactory;

public class MainViewController extends BaseController {

    @FXML
    private ToggleButton optionsButton;

    public MainViewController(String fxmlName) {
        super(fxmlName);
    }

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

    @FXML
    void leftPanelButtonWeeklyForecastOnAction() {
        ViewFactory.switchCenterViewToWeeklyForecastView();
    }
}
