module pl.adrian_komuda {
    requires javafx.controls;
    requires javafx.fxml;
//    requires spring.expression;
//    requires spring.aop;
//    requires spring.asm;
//    requires spring.beans;
//    requires spring.core;
//    requires spring.context;
    requires spring.web;
//    requires com.fasterxml.jackson.core;
//    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens pl.adrian_komuda;
    opens pl.adrian_komuda.controllers;
    opens pl.adrian_komuda.weather_client;
    opens pl.adrian_komuda.weather_client.data_transfer_objects;
    exports pl.adrian_komuda;

}