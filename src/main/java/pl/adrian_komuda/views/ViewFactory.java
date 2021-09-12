package pl.adrian_komuda.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
        switchCenterViewToWeatherView();
        STAGE.setScene(SCENE);
        STAGE.setMinWidth(1124);
        STAGE.setMinHeight(700);
        STAGE.setMaximized(false);
        STAGE.show();
    }

    public static void switchCenterViewToWeatherView() {
        MAIN_VIEW.setCenter(loadFXML("WeatherView"));
    }

    public static void switchCenterViewToAddCityView() {
        MAIN_VIEW.setCenter(loadFXML("AddCityView"));
    }

    public static void switchCenterViewToDeleteCityView() {
        MAIN_VIEW.setCenter(loadFXML("DeleteCityView"));
    }

    public static void switchCenterViewToOptions() {
        MAIN_VIEW.setCenter(loadFXML("OptionsView"));
    }

    public static void showAbout() {
        Stage aboutStage = new Stage();
        Scene aboutScene = new Scene(loadFXML("AboutView"));
        aboutStage.setScene(aboutScene);
        aboutStage.setResizable(false);
        aboutStage.initStyle(StageStyle.UTILITY);
        aboutStage.show();
    }
}
