package pl.adrian_komuda.controllers.persistence;

import pl.adrian_komuda.model.ColorTheme;

public class ColorThemeToFile implements ObjectToSave, ObjectToLoad {
    private ColorTheme colorTheme;

    private final static String DIRECTORY_LOCATION = "saved_data\\options_data";
    private final static String FILE_LOCATION = DIRECTORY_LOCATION + "\\color_theme.ser";

    public ColorThemeToFile() {}

    public ColorThemeToFile(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    @Override
    public String getFileLocation() {
        return FILE_LOCATION;
    }

    @Override
    public String getDirectoryLocation() {
        return DIRECTORY_LOCATION;
    }
}
