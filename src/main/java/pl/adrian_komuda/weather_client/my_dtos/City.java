package pl.adrian_komuda.weather_client.my_dtos;

import java.io.Serializable;

public interface City extends Serializable {

    String getName();

    float getLatitude();

    float getLongitude();
}
