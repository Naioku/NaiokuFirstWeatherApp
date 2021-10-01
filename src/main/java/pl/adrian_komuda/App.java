package pl.adrian_komuda;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.adrian_komuda.Views.ViewFactory;

import java.io.IOException;
import java.util.Locale;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static BorderPane mainView;

    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(new Locale("en"));
        ViewFactory.showMainWindow();
    }

    public static void main(String[] args) {
        launch();
    }

}