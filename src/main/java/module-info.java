module pl.adrian_komuda {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.adrian_komuda;
    opens pl.adrian_komuda.controllers;
    exports pl.adrian_komuda;

}