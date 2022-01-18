package pl.adrian_komuda.weather_client.my_dtos;

import java.util.Objects;

public class SpecificCity implements City {
    private final String name;
    private final float latitude;
    private final float longitude;

    public SpecificCity(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificCity that = (SpecificCity) o;
        return Float.compare(that.latitude, latitude) == 0 && Float.compare(that.longitude, longitude) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }
}
