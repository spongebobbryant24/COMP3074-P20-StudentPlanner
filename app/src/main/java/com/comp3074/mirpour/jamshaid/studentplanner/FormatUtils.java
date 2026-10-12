package com.comp3074.mirpour.jamshaid.studentplanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class FormatUtils {
    private FormatUtils() { }

    public static String displayDate(String iso) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(iso);
            return new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(date);
        } catch (Exception ignored) {
            return iso;
        }
    }

    public static String displayTime(String hhmm) {
        try {
            Date time = new SimpleDateFormat("HH:mm", Locale.US).parse(hhmm);
            return new SimpleDateFormat("h:mm a", Locale.US).format(time);
        } catch (Exception ignored) {
            return hhmm;
        }
    }

    public static String paddedTime(int hour, int minute) {
        return String.format(Locale.US, "%02d:%02d", hour, minute);
    }

    public static String paddedDate(int year, int monthOneBased, int day) {
        return String.format(Locale.US, "%04d-%02d-%02d", year, monthOneBased, day);
    }
}
