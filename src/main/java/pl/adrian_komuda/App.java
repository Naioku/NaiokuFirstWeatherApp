package pl.adrian_komuda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static BorderPane mainView;

    @Override
    public void start(Stage stage) throws IOException {
        mainView = (BorderPane) loadFXML("MainView");
        SplitPane weatherView = (SplitPane) loadFXML("WeatherView");
        mainView.setCenter(weatherView);

        scene = new Scene(mainView);
        stage.setScene(scene);
        stage.setMinWidth(865);
        stage.setMinHeight(583);
        stage.setMaximized(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}