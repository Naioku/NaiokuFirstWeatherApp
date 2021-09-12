package pl.adrian_komuda;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelpingMethods {
    public static String convertUNIXSecondsToHourString(long seconds) {
        // convert seconds to milliseconds
        Date date = new java.util.Date(seconds*1000L);
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}
