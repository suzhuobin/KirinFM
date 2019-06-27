package net.lzzy.kirinfm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Administrator
 */
public class DateTimeUtils {
    public static final SimpleDateFormat DATE_TIME_DEMAND =
            new SimpleDateFormat("yyyyMMdd", Locale.CANADA);
    public static final SimpleDateFormat DATE_TIME_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
    public static final SimpleDateFormat DATE_THIS_FORMAT =
            new SimpleDateFormat("HH:mm:ss", Locale.CANADA);

    public static boolean playIngIf(String startTime, String endTime) throws ParseException {
        String date = DATE_FORMAT.format(new Date());
        long startDate = DATE_TIME_FORMAT.parse(date + " " + startTime).getTime();
        long thisDate = System.currentTimeMillis();
        long endDate = DATE_TIME_FORMAT.parse(date + " " + endTime).getTime();
        return thisDate > startDate && thisDate < endDate;
    }

    public static boolean playIf(String startTime) throws ParseException {
        String date = DATE_FORMAT.format(new Date());
        long thisDate = System.currentTimeMillis();
        long startDate = DATE_TIME_FORMAT.parse(date + " " + startTime).getTime();
        return thisDate >= startDate;
    }

    public static String getDay(Date today) {
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        return String.valueOf(c.get(Calendar.DAY_OF_WEEK));
    }
}
