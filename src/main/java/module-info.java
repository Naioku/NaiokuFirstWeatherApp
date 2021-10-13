module pl.adrian_komuda {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    opens pl.adrian_komuda;
    opens pl.adrian_komuda.controllers;
    opens pl.adrian_komuda.controllers.persistence;
    opens pl.adrian_komuda.model;
    opens pl.adrian_komuda.weather_client;
    opens pl.adrian_komuda.weather_client.api_dtos;
    opens pl.adrian_komuda.utilities;
    exports pl.adrian_komuda;
    exports pl.adrian_komuda.utilities;
    exports pl.adrian_komuda.utilities.custom_exceptions;
    opens pl.adrian_komuda.weather_client.my_dtos;
}