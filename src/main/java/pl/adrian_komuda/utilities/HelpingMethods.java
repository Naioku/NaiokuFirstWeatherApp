package pl.adrian_komuda.utilities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class HelpingMethods {
    private static final DateTimeFormatter HOUR_MINUTE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter MONTH_DAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MM.dd (EEE)");

    /**
     * Timezone offset included in getting data fom weather client.
     */
    public static String convertUNIXSecondsToHourString(long seconds) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(seconds*1000L), ZoneId.of("GMT+0"));
        return dateTime.format(HOUR_MINUTE_TIME_FORMAT);
    }

    /**
     * Timezone offset included in getting data fom weather client.
     */
    public static String convertUNIXSecondsToDate(long seconds) {
        LocalDate date = Instant.ofEpochMilli(seconds*1000L).atZone(ZoneId.of("GMT+0")).toLocalDate();
        return date.format(MONTH_DAY_DATE_FORMAT);
    }

    public static float mPerSecToKmPerH(float speed) {
        return (float) (speed * 3.6);
    }
}
