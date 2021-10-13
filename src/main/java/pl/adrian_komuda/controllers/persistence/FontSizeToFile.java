package pl.adrian_komuda.controllers.persistence;

import pl.adrian_komuda.model.FontSize;

public class FontSizeToFile implements ObjectToSave, ObjectToLoad {
    private FontSize fontSize;

    private final static String DIRECTORY_LOCATION = "saved_data\\options_data";
    private final static String FILE_LOCATION = DIRECTORY_LOCATION + "\\font_size.ser";

    public FontSizeToFile() {}

    public FontSizeToFile(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public FontSize getFontSize() {
        return fontSize;
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
