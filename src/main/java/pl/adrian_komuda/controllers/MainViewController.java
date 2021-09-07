package pl.adrian_komuda.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import pl.adrian_komuda.App;
import pl.adrian_komuda.views.ViewFactory;

import java.io.IOException;

public class MainViewController {

    @FXML
    private BorderPane borderPane;

    @FXML
    void mainMenuAddCityAction() {
        ViewFactory.changeToAddCityView();
    }

    @FXML
    void mainMenuDeleteCityAction() {
        ViewFactory.changeToDeleteCityView();
    }

    @FXML
    public void mainMenuCloseAction() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void leftPanelButtonAddCityOnAction() {
        ViewFactory.changeToAddCityView();
    }

    @FXML
    void leftPanelButtonDeleteCityOnAction() {
        ViewFactory.changeToDeleteCityView();
    }

    @FXML
    void leftPanelButtonWeatherOnAction() {
        ViewFactory.changeToWeatherView();
    }
}
