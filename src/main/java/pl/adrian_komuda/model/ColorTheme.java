package pl.adrian_komuda.model;

public enum ColorTheme {
    LIGHT("css/themeLight.css"),
    DARK("css/themeDark.css"),
    DEFAULT("css/themeDefault.css");

    private final String cssPath;

    ColorTheme(String cssPath) {
        this.cssPath = cssPath;
    }

    public String getCssPath() {
        return cssPath;
    }
}
