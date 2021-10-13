package pl.adrian_komuda.utilities;

public class ErrorMessages {
    public static final String WEATHER_API_COULD_NOT_LOAD_CITY_DATA = "Error with loading city values. " +
            "It's a application error. Try again later. If this error will repeat, please, contact the creator.";

    public static String WEATHER_API_COULD_NOT_LOAD_DATA = "Error with loading weather values. " +
            "Try to change city and back to preview. Eventually delete and add that city again.";

    public static String WEATHER_API_TYPO_IN_ADDING_LOCATION = "You have probably made typo in adding new locale " +
            "or entered names in language different from what You have been set.";

    public static String WEATHER_API_TYPO_IN_ADDING_CITY = "No such a city. Probably it is a typo in city name.";
}
