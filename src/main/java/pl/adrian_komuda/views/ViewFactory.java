package pl.adrian_komuda.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.adrian_komuda.App;

import java.io.IOException;

public class ViewFactory {
    private static final BorderPane MAIN_VIEW = (BorderPane) loadFXML("MainView");
    private static final Scene SCENE = new Scene(MAIN_VIEW);
    private static final Stage STAGE = new Stage();

    private static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/fxml/" + fxml + ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error with loadFXML function!");
            return null;
        }
    }

    public static void showMainWindow() {
        changeToWeatherView();
        STAGE.setScene(SCENE);
        STAGE.setMinWidth(865);
        STAGE.setMinHeight(583);
        STAGE.setMaximized(false);
        STAGE.show();
    }

    public static void changeToWeatherView() {
        MAIN_VIEW.setCenter(loadFXML("WeatherView"));
    }

    public static void changeToAddCityView() {
        MAIN_VIEW.setCenter(loadFXML("AddCityView"));
    }

    public static void changeToDeleteCityView() {
        MAIN_VIEW.setCenter(loadFXML("DeleteCityView"));
    }
}
