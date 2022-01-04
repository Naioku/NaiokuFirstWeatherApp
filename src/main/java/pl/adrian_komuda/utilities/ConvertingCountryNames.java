package pl.adrian_komuda.utilities;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConvertingCountryNames {
    Map<String, String> countries;

    public ConvertingCountryNames() {
        setupCountries();
    }

    /**
     * Use it, when user changes the Locale.
     */
    public void setupCountries() {
        this.countries = new HashMap<>();

        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale(Locale.getDefault().getLanguage(), iso);
            countries.put(l.getDisplayCountry(l), iso);
        }
    }

    public String convertNameToISO(String name) throws IllegalArgumentException {
        String response = countries.get(name);
        if (response == null) {
            throw new IllegalArgumentException();
        }
        return response;
    }

    /**
     * Prints all possible locales in particular language and they shortcuts.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("==== Possible locales ====");
        System.out.println();
        for (Map.Entry<String, String> entry : countries.entrySet()) {
            result.append("\n\nkey: ").append(entry.getKey());
            result.append("\nvalue: ").append(entry.getValue());
        }
        return result.toString();
    }
}
