package pl.adrian_komuda.Model;

public class EmptyCity implements City {
    private final String name = "None";
    private final float latitude = 0F;
    private final float longitude = 0F;

    @Override
    public void setName(String name) {

    }

    @Override
    public void setLatitude(float latitude) {

    }

    @Override
    public void setLongitude(float longitude) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return name;
    }
}
