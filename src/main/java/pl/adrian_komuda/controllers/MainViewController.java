package pl.adrian_komuda.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import pl.adrian_komuda.App;

import java.io.IOException;

public class MainViewController {

    @FXML
    private BorderPane borderPane;

    @FXML
    void addCityAction() throws IOException {
        borderPane.setCenter(App.loadFXML("AddCityView"));
    }
}
