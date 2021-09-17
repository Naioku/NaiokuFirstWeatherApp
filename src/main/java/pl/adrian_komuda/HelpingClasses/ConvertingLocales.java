package pl.adrian_komuda.HelpingClasses;

import pl.adrian_komuda.HelpingClasses.CustomExceptions.NoSuchItemInMapException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConvertingLocales {
    Map<String, String> countries;

    public ConvertingLocales() {
        this.countries = new HashMap<>();

        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale(Locale.getDefault().getLanguage(), iso);
            countries.put(l.getDisplayCountry(l), iso);
        }
    }

    public String convertNameToISO(String name) throws NoSuchItemInMapException {
        String response = countries.get(name);
        if (response == null) {
            throw new NoSuchItemInMapException();
        }
        return response;
    }

    public void printAllPossibleLocales() {
        System.out.println("==== Possible locales ====");
        for (Map.Entry<String, String> entry : countries.entrySet()) {
            System.out.println("\nkey: " + entry.getKey());
            System.out.println("value: " + entry.getValue());
        }


    }
}
