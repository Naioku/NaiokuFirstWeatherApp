package pl.adrian_komuda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HelpingMethods {
    public static String convertUNIXSecondsToHourString(long seconds) {
        // convert seconds to milliseconds
        Date date = new java.util.Date(seconds*1000L);
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0")); // Timezone offset included in getting data fom weather client.
        return sdf.format(date);
    }

    public static String convertUNIXSecondsToDate(long seconds) {
        Date date = new java.util.Date(seconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM.dd (EEE)");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0")); // Timezone offset included in getting data fom weather client.
        return sdf.format(date);
    }

    public static float mPerSecToKmPerH(float speed) {
        return (float) (speed * 3.6);
    }
}
