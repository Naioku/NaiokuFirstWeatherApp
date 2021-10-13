package pl.adrian_komuda.weather_client.my_dtos;

public class HourlyWeatherDto {
    private final long dateTime;
    private final int temperature;
    private final int uvi;
    private final String description;
    private final String icon;

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
