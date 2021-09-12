package pl.adrian_komuda.model;

public class HourlyWeatherDto {
    private long dateTime = 0;
    private int temperature = 0;
    private int uvi = 0;
    private String description = "";
    private String icon = "";

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = Math.round(temperature);
    }

    public int getUvi() {
        return uvi;
    }

    public void setUvi(int uvi) {
        this.uvi = uvi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
