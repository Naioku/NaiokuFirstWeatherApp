package pl.adrian_komuda.Model;

public class HourlyWeatherDto {
    private long dateTime = 0;
    private int temperature = 0;
    private int uvi = 0;
    private String description = "";
    private String icon = "";

    public HourlyWeatherDto(long dateTime, long dateTimeOffset, float temperature, int uvi, String description, String icon) {
        this.dateTime = dateTime + dateTimeOffset;
        this.temperature = Math.round(temperature);
        this.uvi = uvi;
        this.description = description;
        this.icon = icon;
    }

    public long getDateTime() {
        return dateTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getUvi() {
        return uvi;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
