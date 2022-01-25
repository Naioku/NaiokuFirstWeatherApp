package pl.adrian_komuda.model;

import javafx.collections.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import pl.adrian_komuda.controllers.persistence.LocationsToFile;
import pl.adrian_komuda.controllers.persistence.PersistenceAccess;
import pl.adrian_komuda.utilities.ErrorDialogsContent;
import pl.adrian_komuda.views.ViewFactory;
import pl.adrian_komuda.weather_client.my_dtos.City;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CustomLocations {

    private static CustomLocations customLocations;
    private final ObservableMap<String, ObservableList<City>> countriesCitiesMap;

    private CustomLocations() {
        ObservableMap<String, ObservableList<City>> countriesCitiesMapTemp;
        try {
            LocationsToFile locationsToFile = (LocationsToFile) PersistenceAccess.loadDataFromFile(new LocationsToFile());
            countriesCitiesMapTemp = locationsToFile.getLocations();
        } catch (FileNotFoundException e) {
            countriesCitiesMapTemp = FXCollections.observableMap(new HashMap<>());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            countriesCitiesMapTemp = FXCollections.observableMap(new HashMap<>());
            ViewFactory.throwErrorDialog(ErrorDialogsContent.GENERAL, e);
        }
        countriesCitiesMap = countriesCitiesMapTemp;
    }

    public static CustomLocations getCustomLocations() {
        if (customLocations == null) {
            customLocations = new CustomLocations();
        }

        return customLocations;
    }

    /**
     * First method You need to run.
     * @param treeView - treeView object to upload.
     */
    public void boundCustomLocationsWithTreeView(TreeView<String> treeView) {
        addCountriesCitiesMapChangeListener(treeView);
        if (!countriesCitiesMap.isEmpty()) {
            for (Map.Entry<String, ObservableList<City>> entry : countriesCitiesMap.entrySet()) {
                addCitiesListChangeListener(treeView, entry.getValue());
            }
        }
        refreshTreeView(treeView); // It is important, because the map have to be added to treeView on every load the add/delete view!
    }

    public void addLocation(TreeView<String> treeView, String country, City city) {
        ObservableList<City> cities = countriesCitiesMap.get(country);

        if (cities == null) {
            cities = FXCollections.observableArrayList();
            addCitiesListChangeListener(treeView, cities);
        }
        cities.add(city);
        countriesCitiesMap.put(country, cities);
    }

    public void deleteLocation(TreeView<String> treeView) {
        TreeItem<String> selectedTreeItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedTreeItem.getChildren().isEmpty()) {
            removeCityFromMap(selectedTreeItem);
        }
        else {
            removeCountryFromMap(selectedTreeItem);
        }
    }

    public void refreshTreeView(TreeView<String> treeView) {
        TreeItem<String> root = getRootFromTreeView(treeView);
        ObservableList<TreeItem<String>> countryTreeItemsList = root.getChildren();
        countryTreeItemsList.clear();

        List<String> countriesList = new ArrayList<>(countriesCitiesMap.keySet());
        Collections.sort(countriesList);

        for (String country : countriesList) {
            addCountryWithCitiesToTheTreeView(treeView, country, countriesCitiesMap.get(country).sorted(), root);
        }
    }

    public void saveLocationsToFile() {
        try {
            PersistenceAccess.saveDataToFile(new LocationsToFile(countriesCitiesMap));
        } catch (IOException e) {
            e.printStackTrace();
            ViewFactory.throwErrorDialog(ErrorDialogsContent.GENERAL, e);
        }
    }

    private void addCountryWithCitiesToTheTreeView(TreeView<String> treeView, String country, ObservableList<City> cities, TreeItem<String> root) {
        addNewRecordToRoot(country, cities, root);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    private TreeItem<String> getRootFromTreeView(TreeView<String> treeView) {
        TreeItem<String> root = treeView.getRoot();
        if (root == null) {
            root = new TreeItem<>("Root");
        }
        return root;
    }

    private void addNewRecordToRoot(String country, ObservableList<City> cities, TreeItem<String> root) {
        TreeItem<String> countryTreeItem = new TreeItem<>(country);
        addCitiesToCountryTreeItem(cities, countryTreeItem);
        root.getChildren().add(countryTreeItem);
    }

    // ======= COUNTRY LISTENER START ======= \\
    private void addCountriesCitiesMapChangeListener(TreeView<String> treeView) {
        countriesCitiesMap.addListener(new MapChangeListener<String, ObservableList<City>>() {
            @Override
            public void onChanged(Change<? extends String, ? extends ObservableList<City>> change) {
                // Works only when You add something to map or delete a record. (not add to list which is a value in map)
                if (change.wasAdded()) {
                    addCountryWithCitiesToTheTreeView(treeView, change.getKey(), change.getValueAdded());
                }
                else if (change.wasRemoved()) {
                    removeCountryFromTheTreeView(treeView);
                }
            }
        });
    }

    private void removeCountryFromTheTreeView(TreeView<String> treeView) {
        TreeItem<String> selectedTreeItem = treeView.getSelectionModel().getSelectedItem();
        selectedTreeItem.getParent().getChildren().remove(selectedTreeItem);
    }

    private void addCountryWithCitiesToTheTreeView(TreeView<String> treeView, String country, ObservableList<City> cities) {
        TreeItem<String> root = getRootFromTreeView(treeView);
        addCountryWithCitiesToTheTreeView(treeView, country, cities, root);
    }
    // ======= COUNTRY LISTENER END ======= \\

    // ======= CITY LISTENER START ======= \\
    private void addCitiesListChangeListener(TreeView<String> treeView, ObservableList<City> cities) {
        cities.addListener(new ListChangeListener<City>() {
            @Override
            public void onChanged(Change<? extends City> change) {
                change.next();
                if (change.wasAdded()) {
                    uploadCityTreeItemListToTheTreeView(treeView, change);
                }
                else if (change.wasRemoved()) {
                    uploadCityTreeItemListToTheTreeView(treeView, change);
                }
            }
        });
    }

    private void uploadCityTreeItemListToTheTreeView(TreeView<String> treeView, ListChangeListener.Change<? extends City> change) {
        Map.Entry<String, ObservableList<City>> countryCitiesEntry = findCountryCityEntrySetByList(change.getList());
        if (countryCitiesEntry == null) {
            return;
        }

        String country = countryCitiesEntry.getKey();
        ObservableList<TreeItem<String>> countriesTreeItemList = treeView.getRoot().getChildren();
        TreeItem<String> countryTreeItem = findTreeItemInList(countriesTreeItemList, country);
        if (countryTreeItem == null) {
            return;
        }

        countryTreeItem.getChildren().clear();
        addCitiesToCountryTreeItem(countryCitiesEntry.getValue(), countryTreeItem);
    }

    private Map.Entry<String, ObservableList<City>> findCountryCityEntrySetByList(ObservableList<? extends City> list) {
        for (Map.Entry<String, ObservableList<City>> entry : countriesCitiesMap.entrySet()) {
            if (entry.getValue().equals(list)) {
                return entry;
            }
        }
        return null;
    }

    private TreeItem<String> findTreeItemInList(ObservableList<TreeItem<String>> treeItemList, String itemName) {
        for (TreeItem<String> treeItem : treeItemList) {
            if (treeItem.getValue().equals(itemName)) {
                return treeItem;
            }
        }
        return null;
    }
    // ======= CITY LISTENER END ======= \\

    private void addCitiesToCountryTreeItem(ObservableList<City> cities, TreeItem<String> countryTreeItem) {
        for (City city : cities) {
            TreeItem<String> cityTreeItem = new TreeItem<>(city.getName());
            countryTreeItem.getChildren().add(cityTreeItem);
        }
    }

    // ======= MAP HANDLING START ======= \\
    private void removeCountryFromMap(TreeItem<String> selectedTreeItem) {
        String countryName = selectedTreeItem.getValue();
        countriesCitiesMap.remove(countryName);
    }

    private void removeCityFromMap(TreeItem<String> cityTreeItem) {
        TreeItem<String> countryTreeItem = cityTreeItem.getParent();

        String cityName = cityTreeItem.getValue();
        String countryName = countryTreeItem.getValue();

        ObservableList<City> citiesList = countriesCitiesMap.get(countryName);
        City cityToRemove = findCityInList(cityName, citiesList);
        if (cityToRemove == null) {
            return;
        }

        citiesList.remove(cityToRemove);
        if (citiesList.isEmpty()) removeCountryFromMap(countryTreeItem);
    }

    public ObservableList<City> getCitiesByCountry(String country) {
        return countriesCitiesMap.get(country);
    }

    public City findCityInList(String cityName, ObservableList<City> citiesList) {
        for (City city : citiesList) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    public String findCountryByCity(City cityObj) {
        for (Map.Entry<String, ObservableList<City>> entry : countriesCitiesMap.entrySet()) {
            for (City cityFromList : entry.getValue()) {
                if (cityFromList.equals(cityObj)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
    // ======= MAP HANDLING END ======= \\

    public ObservableMap<String, ObservableList<City>> getCountriesCitiesMap(){
        return countriesCitiesMap;
    }
}
