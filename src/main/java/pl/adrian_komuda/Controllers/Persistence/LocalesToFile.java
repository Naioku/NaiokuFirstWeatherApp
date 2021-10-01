package pl.adrian_komuda.Controllers.Persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import pl.adrian_komuda.Model.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalesToFile implements ObjectToSave, ObjectToLoad {
    private Map<String, List<City>> locales;

    private final String DIRECTORY_LOCATION = "saved_data";
    private final String FILE_LOCATION = DIRECTORY_LOCATION + "\\locales.ser";

    public LocalesToFile() {}

    public LocalesToFile(ObservableMap<String, ObservableList<City>> locales) {
        this.locales = convertObservableLocalesToNonObservable(locales);
    }

    public ObservableMap<String, ObservableList<City>> getLocales() {
        return convertNonObservableLocalesToObservable(locales);
    }

    @Override
    public String getFileLocation() {
        return FILE_LOCATION;
    }

    @Override
    public String getDirectoryLocation() {
        return DIRECTORY_LOCATION;
    }

    private Map<String, List<City>> convertObservableLocalesToNonObservable(ObservableMap<String, ObservableList<City>> observableLocales) {
        Map<String, List<City>> nonObservableLocales = new HashMap<>();
        for (Map.Entry<String, ObservableList<City>> entry : observableLocales.entrySet()) {
            String country = entry.getKey();
            List<City> nonObservableCitiesList = new ArrayList<>(entry.getValue());

            nonObservableLocales.put(country, nonObservableCitiesList);
        }

        return nonObservableLocales;
    }

    private ObservableMap<String, ObservableList<City>> convertNonObservableLocalesToObservable(Map<String, List<City>> nonObservableLocales) {
        ObservableMap<String, ObservableList<City>> observableLocales = FXCollections.observableHashMap();
        for (Map.Entry<String, List<City>> entry : nonObservableLocales.entrySet()) {
            String country = entry.getKey();
            ObservableList<City> nonObservableCitiesList = FXCollections.observableArrayList(entry.getValue());

            observableLocales.put(country, nonObservableCitiesList);
        }

        return observableLocales;
    }
}
