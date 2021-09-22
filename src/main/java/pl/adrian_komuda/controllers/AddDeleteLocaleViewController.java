package pl.adrian_komuda.controllers;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import pl.adrian_komuda.HelpingClasses.ConvertingLocales;
import pl.adrian_komuda.HelpingClasses.CustomExceptions.NoSuchItemInMapException;
import pl.adrian_komuda.model.City;
import pl.adrian_komuda.model.CustomLocales;
import pl.adrian_komuda.weather_client.WeatherClient;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDeleteLocaleViewController implements Initializable {

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TableView<City> tableView;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label errorLabel;

    @FXML
    void addLocaleAction() {
        resetErrorLabel();

        String countryName = countryTextField.getText();
        String cityName = cityTextField.getText();

        ConvertingLocales convertingLocales = new ConvertingLocales();

        try {
            String countryISO = convertingLocales.convertNameToISO(countryName);
            WeatherClient weatherClient = new WeatherClient();

            City cityObj = weatherClient.getCityInfo(cityName, countryISO);
            CustomLocales.addLocale(treeView, countryName, cityObj);

        } catch (NoSuchItemInMapException e) {
            errorLabel.setText("You have probably made typo in adding new locale or entered names in language different from what You have been set.");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            errorLabel.setText("No such a city. Probably it is a typo in city name.");
            e.printStackTrace();
        }
    }

    @FXML
    void deleteLocaleAction() {
        resetErrorLabel();
        CustomLocales.deleteLocale(treeView);
    }

    @FXML
    void refreshAction() {
        CustomLocales.refreshTreeView(treeView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomLocales.boundCustomLocalesWithTreeView(treeView);
        prepareTableViewToShowingTheCityValues();
        addMouseClickListener();
    }

    private void prepareTableViewToShowingTheCityValues() {
        TableColumn nameColumn = tableView.getColumns().get(0);
        TableColumn latitudeColumn = tableView.getColumns().get(1);
        TableColumn longitudeColumn = tableView.getColumns().get(2);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    }

    private void addMouseClickListener() {
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
            handleMouseClicked(event);
        };

        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
    }

    private void handleMouseClicked(MouseEvent event) {
        tableView.getItems().clear();
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (isRealTreeItem(node)) {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();

            if (isCountry(selectedItem)) {
                String countryName = selectedItem.getValue();
                ObservableList<City> cityObjects = CustomLocales.getCitiesByCountry(countryName);

                tableView.getItems().addAll(cityObjects.sorted());
            } else {
                String cityName = selectedItem.getValue();
                String countryName = selectedItem.getParent().getValue();
                ObservableList<City> cities = CustomLocales.getCitiesByCountry(countryName);
                City cityObj = CustomLocales.findCityInList(cityName, cities);

                tableView.getItems().add(cityObj);
            }
        }
    }

    private boolean isRealTreeItem(Node node) {
        return node instanceof Text || (node instanceof TreeCell && ((TreeCell<?>) node).getText() != null);
    }
    private boolean isCountry(TreeItem<?> selectedItem) {
        return selectedItem.getParent().getValue().equals("Root");
    }

    private void resetErrorLabel() {
        errorLabel.setText("FINE");
    }
}
