package pl.adrian_komuda;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.adrian_komuda.views.ViewFactory;

import java.io.IOException;
import java.util.Locale;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Locale.setDefault(new Locale("en"));
        ViewFactory.showMainWindow(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}