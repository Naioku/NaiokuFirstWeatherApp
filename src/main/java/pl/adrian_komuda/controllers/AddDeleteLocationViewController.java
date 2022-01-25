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
import pl.adrian_komuda.model.CustomLocations;
import pl.adrian_komuda.utilities.ConvertingCountryNames;
import pl.adrian_komuda.utilities.ErrorMessages;
import pl.adrian_komuda.utilities.custom_exceptions.ApiException;
import pl.adrian_komuda.weather_client.WeatherClient;
import pl.adrian_komuda.weather_client.my_dtos.City;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDeleteLocationViewController extends BaseController implements Initializable {

    ConvertingCountryNames convertingCountryNames;
    WeatherClient weatherClient;
    CustomLocations customLocations;

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

    public AddDeleteLocationViewController (
            String fxmlName,
            ConvertingCountryNames convertingCountryNames,
            WeatherClient weatherClient,
            CustomLocations customLocations) {

        super(fxmlName);
        this.convertingCountryNames = convertingCountryNames;
        this.weatherClient = weatherClient;
        this.customLocations = customLocations;
    }

    @FXML
    void addLocaleAction() {
        resetErrorLabel();

        String countryName = countryTextField.getText();
        String cityName = cityTextField.getText();

        try {
            String countryISO = convertingCountryNames.convertNameToISO(countryName);
            City cityObj = weatherClient.getCityInfo(cityName, countryISO);
            customLocations.addLocation(treeView, countryName, cityObj);
            customLocations.saveLocationsToFile();

        } catch (IllegalArgumentException e) {
            errorLabel.setText(ErrorMessages.WEATHER_API_TYPO_IN_ADDING_LOCATION);
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            errorLabel.setText(ErrorMessages.WEATHER_API_TYPO_IN_ADDING_CITY);
            e.printStackTrace();
        } catch (ApiException e) {
            errorLabel.setText(ErrorMessages.WEATHER_API_COULD_NOT_LOAD_CITY_DATA);
            e.printStackTrace();
        }
    }

    @FXML
    void deleteLocaleAction() {
        resetErrorLabel();
        customLocations.deleteLocation(treeView);
        customLocations.saveLocationsToFile();
    }

    @FXML
    void refreshAction() {
        customLocations.refreshTreeView(treeView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customLocations.boundCustomLocationsWithTreeView(treeView);
        prepareTableViewToShowingTheCityValues();
        addMouseClickListener();
    }

    private void prepareTableViewToShowingTheCityValues() {
        TableColumn<?, ?> nameColumn = tableView.getColumns().get(0);
        TableColumn<?, ?> latitudeColumn = tableView.getColumns().get(1);
        TableColumn<?, ?> longitudeColumn = tableView.getColumns().get(2);

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
                ObservableList<City> cityObjects = customLocations.getCitiesByCountry(countryName);

                tableView.getItems().addAll(cityObjects.sorted());
            } else {
                String cityName = selectedItem.getValue();
                String countryName = selectedItem.getParent().getValue();
                ObservableList<City> cities = customLocations.getCitiesByCountry(countryName);
                City cityObj = customLocations.findCityInList(cityName, cities);

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
