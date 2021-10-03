package pl.adrian_komuda.Views;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.adrian_komuda.App;
import pl.adrian_komuda.Controllers.*;
import pl.adrian_komuda.Controllers.Persistence.ColorThemeToFile;
import pl.adrian_komuda.Controllers.Persistence.FontSizeToFile;
import pl.adrian_komuda.Controllers.Persistence.PersistenceAccess;
import pl.adrian_komuda.Model.ColorTheme;
import pl.adrian_komuda.Model.FontSize;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {
    private static final BorderPane MAIN_VIEW = (BorderPane) loadFXML(new MainViewController("MainView"));
    private static final Scene SCENE = new Scene(MAIN_VIEW);
    private static final Stage STAGE = new Stage();

    private static final WeatherClient weatherClientHomePanel = new WeatherClient();
    private static final WeatherClient weatherClientAnotherPanel = new WeatherClient();

    private static ColorTheme colorTheme;
    private static FontSize fontSize;
    private static final List<Stage> activeStages = new ArrayList<>();

    static {
        FontSizeToFile fontSizeToFile = (FontSizeToFile) PersistenceAccess.loadDataFromFile(new FontSizeToFile());
        if (fontSizeToFile == null) {
            ViewFactory.fontSize = FontSize.MEDIUM;
        }
        else {
            ViewFactory.fontSize = fontSizeToFile.getFontSize();
        }

        ColorThemeToFile colorThemeToFile = (ColorThemeToFile) PersistenceAccess.loadDataFromFile(new ColorThemeToFile());
        if (colorThemeToFile == null) {
            ViewFactory.colorTheme = ColorTheme.DARK;
        }
        else {
            ViewFactory.colorTheme = colorThemeToFile.getColorTheme();
        }
    }

    public static ColorTheme getColorTheme() {
        return colorTheme;
    }

    public static FontSize getFontSize() {
        return fontSize;
    }

    public static void setColorTheme(ColorTheme colorTheme) {
        ViewFactory.colorTheme = colorTheme;
        PersistenceAccess.saveDataToFile(new ColorThemeToFile(ViewFactory.colorTheme));
    }

    public static void setFontSize(FontSize fontSize) {
        ViewFactory.fontSize = fontSize;
        PersistenceAccess.saveDataToFile(new FontSizeToFile(ViewFactory.fontSize));
    }

    public static Parent loadFXML(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Views/fxml/" + controller.getFxmlName() + ".fxml"));
        fxmlLoader.setController(controller);
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
        STAGE.setMinWidth(1294);
        STAGE.setMinHeight(800);
        STAGE.setMaximized(false);
        STAGE.show();
        activeStages.add(STAGE);
        updateStyles();
    }

    public static void switchCenterViewToWeeklyForecastView() {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);


        BaseController weeklyForecastTop = new WeeklyForecastViewController("WeeklyForecastView", weatherClientHomePanel);
        BaseController weeklyForecastBottom = new WeeklyForecastViewController("WeeklyForecastView", weatherClientAnotherPanel);

        VBox topArea = (VBox) loadFXML(weeklyForecastTop);
        VBox bottomArea = (VBox) loadFXML(weeklyForecastBottom);

        setHeaderLabelName(topArea, "Home city");
        setHeaderLabelName(bottomArea, "Chosen city");

        splitPane.getItems().addAll(topArea, bottomArea);
        MAIN_VIEW.setCenter(splitPane);
    }

    public static void switchCenterViewToWeatherView() {
        SplitPane splitPane = new SplitPane();

        BaseController weatherLeftViewController = new WeatherViewController("WeatherView", weatherClientHomePanel);
        BaseController weatherRightViewController = new WeatherViewController("WeatherView", weatherClientAnotherPanel);

        VBox leftArea = (VBox) loadFXML(weatherLeftViewController);
        VBox rightArea = (VBox) loadFXML(weatherRightViewController);

        setHeaderLabelName(leftArea, "Home city");
        setHeaderLabelName(rightArea, "Chosen city");

        splitPane.getItems().addAll(leftArea, rightArea);
        MAIN_VIEW.setCenter(splitPane);
    }

    private static void setHeaderLabelName(Node nodeLevel0, String text) {
        if (nodeLevel0 instanceof Pane) {
            Pane area = (Pane) nodeLevel0;
            ObservableList<Node> childrenNodes = area.getChildren();
            if (childrenNodes != null) {
                for (Node nodeLevel1 : childrenNodes) {
                    setHeaderLabelName(nodeLevel1, text);
                }
            }
        }

        if (nodeLevel0 instanceof Label) {
            Label label = (Label) nodeLevel0;
            String labelFxId = label.getId();
            if (labelFxId != null && labelFxId.equals("headerLabel")) {
                label.setText(text);
            }
        }
    }

    public static void switchCenterViewToAddDeleteLocaleView() {
        BaseController addDeleteLocaleViewController = new AddDeleteLocaleViewController("AddDeleteLocaleView");
        MAIN_VIEW.setCenter(loadFXML(addDeleteLocaleViewController));
    }

    public static void switchCenterViewToOptions() {
        BaseController optionsViewController = new OptionsViewController("OptionsView");
        MAIN_VIEW.setCenter(loadFXML(optionsViewController));
    }

    public static void showAbout() {
        Stage aboutStage = new Stage();
        BaseController aboutViewController = new AboutViewController("AboutView");
        Scene aboutScene = new Scene(loadFXML(aboutViewController));
        aboutStage.setScene(aboutScene);
        aboutStage.setResizable(false);
        aboutStage.initStyle(StageStyle.UNIFIED);
        aboutStage.show();
        aboutStage.onCloseRequestProperty().setValue(windowEvent -> activeStages.remove(aboutStage));
        activeStages.add(aboutStage);
        updateStyles();
    }

    public static void updateStyles() {
        for (Stage stage : activeStages) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(ViewFactory.class.getResource("css/default.css").toExternalForm());
            scene.getStylesheets().add(ViewFactory.class.getResource(colorTheme.getCssPath()).toExternalForm());
            scene.getStylesheets().add(ViewFactory.class.getResource(fontSize.getCssPath()).toExternalForm());
        }

    }
}
