package pl.adrian_komuda.model;

public interface City {
    public void setName(String name);

    public void setLatitude(float latitude);

    public void setLongitude(float longitude);

    public String getName();

    public float getLatitude();

    public float getLongitude();

    @Override
    public String toString();
}
