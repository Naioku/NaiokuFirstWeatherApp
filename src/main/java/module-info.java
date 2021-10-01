module pl.adrian_komuda {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    opens pl.adrian_komuda;
    opens pl.adrian_komuda.Controllers;
    opens pl.adrian_komuda.Controllers.Persistence;
    opens pl.adrian_komuda.Model;
    opens pl.adrian_komuda.weather_client;
    opens pl.adrian_komuda.weather_client.data_transfer_objects;
    exports pl.adrian_komuda;

}