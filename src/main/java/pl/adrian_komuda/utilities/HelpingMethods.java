package pl.adrian_komuda.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HelpingMethods {
    private static final SimpleDateFormat HOUR_MINUTE_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat MONTH_DAY_DATE_FORMAT = new SimpleDateFormat("MM.dd (EEE)");

    public static String convertUNIXSecondsToHourString(long seconds) {
        // convert seconds to milliseconds
        Date date = new Date(seconds*1000L);
        // the format of your date
        HOUR_MINUTE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+0")); // Timezone offset included in getting data fom weather client.
        return HOUR_MINUTE_TIME_FORMAT.format(date);
    }

    public static String convertUNIXSecondsToDate(long seconds) {
        Date date = new Date(seconds*1000L);
        MONTH_DAY_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+0")); // Timezone offset included in getting data fom weather client.
        return MONTH_DAY_DATE_FORMAT.format(date);
    }

    public static float mPerSecToKmPerH(float speed) {
        return (float) (speed * 3.6);
    }
}
