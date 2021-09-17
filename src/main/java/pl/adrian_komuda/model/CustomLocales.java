package pl.adrian_komuda.model;

import javafx.collections.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.HashMap;
import java.util.Map;

public class CustomLocales {
    private final TreeView<String> treeView;
    private final ObservableMap<String, ObservableList<City>> countriesCitiesMap = FXCollections.observableMap(new HashMap<>());

    public CustomLocales(TreeView<String> treeView) {
        this.treeView = treeView;
        addCountriesCitiesMapChangeListener();
    }

    public void addLocale(String country, City city) {
        ObservableList<City> cities = countriesCitiesMap.get(country);

        if (cities == null) {
            cities = FXCollections.observableArrayList();
            addCitiesListChangeListener(cities);
        }
        cities.add(city);
        countriesCitiesMap.put(country, cities);
    }

    public void deleteLocale() {
        TreeItem<String> selectedTreeItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedTreeItem.getChildren().isEmpty()) {
            removeCityFromMap(selectedTreeItem);
        }
        else {
            removeCountryFromMap(selectedTreeItem);
        }
    }

    // ======= COUNTRY LISTENER START ======= \\
    private void addCountriesCitiesMapChangeListener() {
        countriesCitiesMap.addListener(new MapChangeListener<String, ObservableList<City>>() {
            @Override
            public void onChanged(Change<? extends String, ? extends ObservableList<City>> change) {
                // Works only when You add something to map or delete a record. (not add to list which is a value in map)
                if (change.wasAdded()) addCountryWithCitiesToTheTreeView(change);
                else if (change.wasRemoved()) removeCountryFromTheTreeView();
            }
        });
    }

    private void removeCountryFromTheTreeView() {
        TreeItem<String> selectedTreeItem = treeView.getSelectionModel().getSelectedItem();
        selectedTreeItem.getParent().getChildren().remove(selectedTreeItem);
    }

    private void addCountryWithCitiesToTheTreeView(MapChangeListener.Change<? extends String,? extends ObservableList<City>> change) {
        TreeItem<String> root = treeView.getRoot();
        if (root == null) {
            root = new TreeItem<>("Root");
        }
        addNewRecordToRoot(change.getKey(), change.getValueAdded(), root);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    private void addNewRecordToRoot(String country, ObservableList<City> cities, TreeItem<String> root) {
        TreeItem<String> countryTreeItem = new TreeItem<>(country);
        addCitiesToCountryTreeItem(cities, countryTreeItem);
        root.getChildren().add(countryTreeItem);
    }
    // ======= COUNTRY LISTENER END ======= \\

    // ======= CITY LISTENER START ======= \\
    private void addCitiesListChangeListener(ObservableList<City> cities) {
        cities.addListener(new ListChangeListener<City>() {
            @Override
            public void onChanged(Change<? extends City> change) {
                change.next();
                if (change.wasAdded()) uploadCityTreeItemListToTheTreeView(change);
                if (change.wasRemoved()) uploadCityTreeItemListToTheTreeView(change);
            }
        });
    }

    private void uploadCityTreeItemListToTheTreeView(ListChangeListener.Change<? extends City> change) {
        Map.Entry<String, ObservableList<City>> countryCitiesEntry = findCountryCityEntrySetByList(change.getList());
        if (countryCitiesEntry == null) return;

        String country = countryCitiesEntry.getKey();
        ObservableList<TreeItem<String>> countriesTreeItemList = treeView.getRoot().getChildren();
        TreeItem<String> countryTreeItem = findTreeItemInList(countriesTreeItemList, country);
        if (countryTreeItem == null) return;

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
        if (cityToRemove == null) return;

        citiesList.remove(cityToRemove);
        if (citiesList.isEmpty()) removeCountryFromMap(countryTreeItem);
    }

    private City findCityInList(String cityName, ObservableList<City> citiesList) {
        for (City city : citiesList) {
            if (city.getName().equals(cityName)) return city;
        }
        return null;
    }
    // ======= MAP HANDLING END ======= \\

    public void printAllSavedLocales() {
        for (Map.Entry<String, ObservableList<City>> entry : countriesCitiesMap.entrySet()) {
            System.out.println("Country: " + entry.getKey());
            for (City city : entry.getValue()) {
                System.out.println("  + name: " + city.getName());
                System.out.println("    + lat:" + city.getLatitude());
                System.out.println("    + lon:" + city.getLongitude());
            }
        }
    }
}
