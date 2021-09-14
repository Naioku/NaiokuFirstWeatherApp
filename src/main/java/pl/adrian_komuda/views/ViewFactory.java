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
import pl.adrian_komuda.Debug;

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
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPosition(1, 0.5D);

        VBox leftArea = (VBox) loadFXML("WeatherView");
        VBox rightArea = (VBox) loadFXML("WeatherView");

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
