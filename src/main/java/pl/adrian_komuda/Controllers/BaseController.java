package pl.adrian_komuda.Controllers;

public class BaseController {
    private final String fxmlName;

    public BaseController(String fxmlName) {
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
