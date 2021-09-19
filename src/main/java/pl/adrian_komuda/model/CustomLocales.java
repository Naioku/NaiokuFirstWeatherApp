package pl.adrian_komuda.model;

import javafx.collections.*;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.*;

public class CustomLocales {
    private static final ObservableMap<String, ObservableList<City>> countriesCitiesMap = FXCollections.observableMap(new HashMap<>());

    /**
     * First method You need to run.
     * @param treeView - treeView object to upload.
     */
    public static void initializeCustomLocales(TreeView<String> treeView) {
        addCountriesCitiesMapChangeListener(treeView);
        refreshTreeView(treeView);
    }

    public static void addLocale(TreeView<String> treeView, String country, City city) {
        ObservableList<City> cities = countriesCitiesMap.get(country);

        if (cities == null) {
            cities = FXCollections.observableArrayList();
            addCitiesListChangeListener(treeView, cities);
        }
        cities.add(city);
        countriesCitiesMap.put(country, cities);
    }

    public static void deleteLocale(TreeView<String> treeView) {
        TreeItem<String> selectedTreeItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedTreeItem.getChildren().isEmpty()) {
            removeCityFromMap(selectedTreeItem);
        }
        else {
            removeCountryFromMap(selectedTreeItem);
        }
    }

    public static void refreshTreeView(TreeView<String> treeView) {
        TreeItem<String> root = getRootFromTreeView(treeView);
        ObservableList<TreeItem<String>> countryTreeItemsList = root.getChildren();
        countryTreeItemsList.clear();

        List<String> countriesList = new ArrayList<>(countriesCitiesMap.keySet());
        Collections.sort(countriesList);

        for (String country : countriesList) {
            addCountryWithCitiesToTheTreeView(treeView, country, countriesCitiesMap.get(country).sorted(), root);
        }
    }

    private static void addCountryWithCitiesToTheTreeView(TreeView<String> treeView, String country, ObservableList<City> cities, TreeItem<String> root) {
        addNewRecordToRoot(country, cities, root);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    private static TreeItem<String> getRootFromTreeView(TreeView<String> treeView) {
        TreeItem<String> root = treeView.getRoot();
        if (root == null) {
            root = new TreeItem<>("Root");
        }
        return root;
    }

    private static void addNewRecordToRoot(String country, ObservableList<City> cities, TreeItem<String> root) {
        TreeItem<String> countryTreeItem = new TreeItem<>(country);
        addCitiesToCountryTreeItem(cities, countryTreeItem);
        root.getChildren().add(countryTreeItem);
    }

    // ======= COUNTRY LISTENER START ======= \\
    private static void addCountriesCitiesMapChangeListener(TreeView<String> treeView) {
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

    private static void removeCountryFromTheTreeView(TreeView<String> treeView) {
        TreeItem<String> selectedTreeItem = treeView.getSelectionModel().getSelectedItem();
        selectedTreeItem.getParent().getChildren().remove(selectedTreeItem);
    }

    private static void addCountryWithCitiesToTheTreeView(TreeView<String> treeView, String country, ObservableList<City> cities) {
        TreeItem<String> root = getRootFromTreeView(treeView);
        addCountryWithCitiesToTheTreeView(treeView, country, cities, root);
    }
    // ======= COUNTRY LISTENER END ======= \\

    // ======= CITY LISTENER START ======= \\
    private static void addCitiesListChangeListener(TreeView<String> treeView, ObservableList<City> cities) {
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

    private static void uploadCityTreeItemListToTheTreeView(TreeView<String> treeView, ListChangeListener.Change<? extends City> change) {
        Map.Entry<String, ObservableList<City>> countryCitiesEntry = findCountryCityEntrySetByList(change.getList());
        if (countryCitiesEntry == null) return;

        String country = countryCitiesEntry.getKey();
        ObservableList<TreeItem<String>> countriesTreeItemList = treeView.getRoot().getChildren();
        TreeItem<String> countryTreeItem = findTreeItemInList(countriesTreeItemList, country);
        if (countryTreeItem == null) return;

        countryTreeItem.getChildren().clear();
        addCitiesToCountryTreeItem(countryCitiesEntry.getValue(), countryTreeItem);
    }

    private static Map.Entry<String, ObservableList<City>> findCountryCityEntrySetByList(ObservableList<? extends City> list) {
        for (Map.Entry<String, ObservableList<City>> entry : countriesCitiesMap.entrySet()) {
            if (entry.getValue().equals(list)) {
                return entry;
            }
        }
        return null;
    }

    private static TreeItem<String> findTreeItemInList(ObservableList<TreeItem<String>> treeItemList, String itemName) {
        for (TreeItem<String> treeItem : treeItemList) {
            if (treeItem.getValue().equals(itemName)) {
                return treeItem;
            }
        }
        return null;
    }
    // ======= CITY LISTENER END ======= \\

    private static void addCitiesToCountryTreeItem(ObservableList<City> cities, TreeItem<String> countryTreeItem) {
        for (City city : cities) {
            TreeItem<String> cityTreeItem = new TreeItem<>(city.getName());
            countryTreeItem.getChildren().add(cityTreeItem);
        }
    }

    // ======= MAP HANDLING START ======= \\
    private static void removeCountryFromMap(TreeItem<String> selectedTreeItem) {
        String countryName = selectedTreeItem.getValue();
        countriesCitiesMap.remove(countryName);
    }

    private static void removeCityFromMap(TreeItem<String> cityTreeItem) {
        TreeItem<String> countryTreeItem = cityTreeItem.getParent();

        String cityName = cityTreeItem.getValue();
        String countryName = countryTreeItem.getValue();

        ObservableList<City> citiesList = countriesCitiesMap.get(countryName);
        City cityToRemove = findCityInList(cityName, citiesList);
        if (cityToRemove == null) return;

        citiesList.remove(cityToRemove);
        if (citiesList.isEmpty()) removeCountryFromMap(countryTreeItem);
    }

    private static City findCityInList(String cityName, ObservableList<City> citiesList) {
        for (City city : citiesList) {
            if (city.getName().equals(cityName)) return city;
        }
        return null;
    }
    // ======= MAP HANDLING END ======= \\

    public static ObservableMap<String, ObservableList<City>> getCountriesCitiesMap(){
        return countriesCitiesMap;
    }

    public static void printAllSavedLocales() {
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
