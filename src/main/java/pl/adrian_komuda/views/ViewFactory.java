package pl.adrian_komuda.views;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
import pl.adrian_komuda.controllers.*;
import pl.adrian_komuda.model.ColorTheme;
import pl.adrian_komuda.model.FontSize;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {
    private static final BorderPane MAIN_VIEW = (BorderPane) loadFXML(new MainViewController("MainView"));
    private static final Scene SCENE = new Scene(MAIN_VIEW);
    private static final Stage STAGE = new Stage();

    private static final WeatherClient weatherClientLeftPanel = new WeatherClient();
    private static final WeatherClient weatherClientRightPanel = new WeatherClient();

    private static ColorTheme colorTheme = ColorTheme.LIGHT;
    private static FontSize fontSize = FontSize.SMALL;
    private static List<Stage> activeStages = new ArrayList<>();

    public static ColorTheme getColorTheme() {
        return colorTheme;
    }

    public static FontSize getFontSize() {
        return fontSize;
    }

    public static void setColorTheme(ColorTheme colorTheme) {
        ViewFactory.colorTheme = colorTheme;
    }

    public static void setFontSize(FontSize fontSize) {
        ViewFactory.fontSize = fontSize;
    }

    private static Parent loadFXML(BaseController controller) {
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

    public static void showMainWindow() {
        switchCenterViewToWeatherView();
        STAGE.setScene(SCENE);
        STAGE.setMinWidth(1214);
        STAGE.setMinHeight(750);
        STAGE.setMaximized(false);
        STAGE.show();
        activeStages.add(STAGE);
        updateStyles();
    }

    public static void switchCenterViewToWeatherView() {
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPosition(1, 0.5D);

        BaseController weatherLeftViewController = new WeatherViewController("WeatherView", weatherClientLeftPanel);
        BaseController weatherRightViewController = new WeatherViewController("WeatherView", weatherClientRightPanel);

        VBox leftArea = (VBox) loadFXML(weatherLeftViewController);
        VBox rightArea = (VBox) loadFXML(weatherRightViewController);

        setHeaderLabelName(leftArea, "Home city");
        setHeaderLabelName(rightArea, "Chosen city");

        splitPane.getItems().addAll(leftArea, rightArea);
        MAIN_VIEW.setCenter(splitPane);
    }

    private static void setHeaderLabelName(VBox area, String text) {
        Node headerVBox = getChildWithProvidedFxId(area, "headerVBox");

        if (headerVBox != null) {
            Node headerLabelNode = getChildWithProvidedFxId((Pane) headerVBox, "headerLabel");
            if (headerLabelNode != null) {
                Label textLabel = (Label) headerLabelNode;
                textLabel.setText(text);
            }
        }
    }

    private static Node getChildWithProvidedFxId(Pane parentNode, String fxid) {
        ObservableList<Node> childrenNodes = parentNode.getChildren();
        for (Node child : childrenNodes) {
            String childFxId = child.getId();
            if (childFxId != null &&
                    childFxId.equals(fxid)) {
                return child;
            }
        }
        return null;
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
        aboutStage.initStyle(StageStyle.UTILITY);
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
