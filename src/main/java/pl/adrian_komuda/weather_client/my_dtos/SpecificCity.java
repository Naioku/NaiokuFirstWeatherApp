package pl.adrian_komuda.weather_client.my_dtos;

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
}
