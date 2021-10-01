package pl.adrian_komuda.Controllers.Persistence;

import pl.adrian_komuda.Model.ColorTheme;

public class ColorThemeToFile implements ObjectToSave, ObjectToLoad {
    private ColorTheme colorTheme;

    private final String DIRECTORY_LOCATION = "saved_data\\options_data";
    private final String FILE_LOCATION = DIRECTORY_LOCATION + "\\color_theme.ser";

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
