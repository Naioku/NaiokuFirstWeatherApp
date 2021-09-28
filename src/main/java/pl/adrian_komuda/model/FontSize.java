package pl.adrian_komuda.model;

public enum FontSize {
    SMALL("css/fontSmall.css"),
    MEDIUM("css/fontMedium.css"),
    BIG("css/fontBig.css");

    private final String cssPath;

    FontSize(String cssPath) {
        this.cssPath = cssPath;
    }

    public String getCssPath() {
        return cssPath;
    }
}
