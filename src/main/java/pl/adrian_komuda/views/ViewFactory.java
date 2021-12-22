package pl.adrian_komuda.views;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.adrian_komuda.App;
import pl.adrian_komuda.controllers.*;
import pl.adrian_komuda.controllers.persistence.ColorThemeToFile;
import pl.adrian_komuda.controllers.persistence.FontSizeToFile;
import pl.adrian_komuda.controllers.persistence.PersistenceAccess;
import pl.adrian_komuda.model.ColorTheme;
import pl.adrian_komuda.model.FontSize;
import pl.adrian_komuda.utilities.ErrorDialogsContent;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {
    private static final int MAIN_WINDOW_HEIGHT = 850;
    private static final int MAIN_WINDOW_WIDTH = 1375;

    private static final BorderPane MAIN_VIEW = (BorderPane) loadFXML(new MainViewController("MainView"));
    private static final Scene SCENE = new Scene(MAIN_VIEW);

    private static final WeatherClient WEATHER_CLIENT_HOME_PANEL = new WeatherClient();
    private static final WeatherClient WEATHER_CLIENT_ANOTHER_PANEL = new WeatherClient();

    private static ColorTheme COLOR_THEME;
    private static FontSize FONT_SIZE;
    private static final List<Stage> ACTIVE_STAGES = new ArrayList<>();

    static {
        FontSize fontSizeTemp;
        try {
            FontSizeToFile fontSizeToFile = (FontSizeToFile) PersistenceAccess.loadDataFromFile(new FontSizeToFile());
            fontSizeTemp = fontSizeToFile.getFontSize();
        } catch (FileNotFoundException e) {
            fontSizeTemp = FontSize.MEDIUM;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            fontSizeTemp = FontSize.MEDIUM;
            ViewFactory.throwErrorDialog(ErrorDialogsContent.GENERAL, e);
        }
        FONT_SIZE = fontSizeTemp;

        ColorTheme colorThemeTemp;
        try {
            ColorThemeToFile colorThemeToFile = (ColorThemeToFile) PersistenceAccess.loadDataFromFile(new ColorThemeToFile());
            colorThemeTemp = colorThemeToFile.getColorTheme();
        } catch (FileNotFoundException e) {
            colorThemeTemp = ColorTheme.DARK;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            colorThemeTemp = ColorTheme.DARK;
            ViewFactory.throwErrorDialog(ErrorDialogsContent.GENERAL, e);
        }
        COLOR_THEME = colorThemeTemp;
    }

    public static ColorTheme getColorTheme() {
        return COLOR_THEME;
    }

    public static FontSize getFontSize() {
        return FONT_SIZE;
    }

    public static void setColorTheme(ColorTheme colorTheme) {
        COLOR_THEME = colorTheme;
        try {
            PersistenceAccess.saveDataToFile(new ColorThemeToFile(ViewFactory.COLOR_THEME));
        } catch (IOException e) {
            e.printStackTrace();
            ViewFactory.throwErrorDialog(ErrorDialogsContent.GENERAL, e);
        }
    }

    public static void setFontSize(FontSize fontSize) {
        FONT_SIZE = fontSize;
        try {
            PersistenceAccess.saveDataToFile(new FontSizeToFile(ViewFactory.FONT_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
            ViewFactory.throwErrorDialog(ErrorDialogsContent.GENERAL, e);
        }
    }

    public static Parent loadFXML(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/fxml/" + controller.getFxmlName() + ".fxml"));
        fxmlLoader.setController(controller);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error with loadFXML function!");
            return null;
        }
    }

    public static void showMainWindow(Stage primaryStage) {
        switchCenterViewToWeatherView();
        primaryStage.setScene(SCENE);
        primaryStage.setMinWidth(MAIN_WINDOW_WIDTH);
        primaryStage.setMinHeight(MAIN_WINDOW_HEIGHT);
        primaryStage.setMaximized(false);
        primaryStage.show();
        ACTIVE_STAGES.add(primaryStage);
        updateStyles();
    }

    public static void switchCenterViewToWeeklyForecastView() {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);


        BaseController weeklyForecastTop = new WeeklyForecastViewController("WeeklyForecastView", WEATHER_CLIENT_HOME_PANEL);
        BaseController weeklyForecastBottom = new WeeklyForecastViewController("WeeklyForecastView", WEATHER_CLIENT_ANOTHER_PANEL);

        VBox topArea = (VBox) loadFXML(weeklyForecastTop);
        VBox bottomArea = (VBox) loadFXML(weeklyForecastBottom);

        setHeaderLabelName(topArea, "Home city");
        setHeaderLabelName(bottomArea, "Chosen city");

        splitPane.getItems().addAll(topArea, bottomArea);
        MAIN_VIEW.setCenter(splitPane);
    }

    public static void switchCenterViewToWeatherView() {
        SplitPane splitPane = new SplitPane();

        BaseController weatherLeftViewController = new WeatherViewController("WeatherView", WEATHER_CLIENT_HOME_PANEL);
        BaseController weatherRightViewController = new WeatherViewController("WeatherView", WEATHER_CLIENT_ANOTHER_PANEL);

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
        aboutStage.onCloseRequestProperty().setValue(windowEvent -> ACTIVE_STAGES.remove(aboutStage));
        ACTIVE_STAGES.add(aboutStage);
        updateStyles();
    }

    public static void updateStyles() {
        for (Stage stage : ACTIVE_STAGES) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(ViewFactory.class.getResource("css/default.css").toExternalForm());
            scene.getStylesheets().add(ViewFactory.class.getResource(COLOR_THEME.getCssPath()).toExternalForm());
            scene.getStylesheets().add(ViewFactory.class.getResource(FONT_SIZE.getCssPath()).toExternalForm());
        }

    }

    public static void throwErrorDialog(
            ErrorDialogsContent errorDialogContent,
            Exception exception
    ) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(errorDialogContent.getHeader());
        alert.setContentText(errorDialogContent.getContent());

        GridPane expandableContent = createAndGetExpandableException(exception);

        alert.getDialogPane().setExpandableContent(expandableContent);
        alert.setX(500D);
        alert.showAndWait();

    }

    private static GridPane createAndGetExpandableException(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expandableContent = new GridPane();
        expandableContent.setMaxWidth(Double.MAX_VALUE);
        expandableContent.add(label, 0, 0);
        expandableContent.add(textArea, 0, 1);

        expandableContent.setMinSize(900D, 556D);
        return expandableContent;
    }
}
