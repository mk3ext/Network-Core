package me.mk3ext.dev.core.punish.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Format {

    public static String MakeStr(long time, int trim) {
        return convertString(Math.max(0L, time), trim, TimeUnit.FIT);
    }

    public static String convertString(long time, int trim, TimeUnit type) {
        if (time == -1L) {
            return "Permanent";
        } else {
            if (type == TimeUnit.FIT) {
                if (time < 60000L) {
                    type = TimeUnit.SECONDS;
                } else if (time < 3600000L) {
                    type = TimeUnit.MINUTES;
                } else if (time < 86400000L) {
                    type = TimeUnit.HOURS;
                } else {
                    type = TimeUnit.DAYS;
                }
            }

            String text;
            double num;
            if (trim == 0) {
                if (type == TimeUnit.DAYS) {
                    text = (num = trim(trim, (double)time / 8.64E7D)) + " Day";
                } else if (type == TimeUnit.HOURS) {
                    text = (num = trim(trim, (double)time / 3600000.0D)) + " Hour";
                } else if (type == TimeUnit.MINUTES) {
                    text = (num = trim(trim, (double)time / 60000.0D)) + " Minute";
                } else if (type == TimeUnit.SECONDS) {
                    text = (int)(num = (double)((int)trim(trim, (double)time / 1000.0D))) + " Second";
                } else {
                    text = (int)(num = (double)((int)trim(trim, (double)time))) + " Millisecond";
                }
            } else if (type == TimeUnit.DAYS) {
                text = (num = trim(trim, (double)time / 8.64E7D)) + " Day";
            } else if (type == TimeUnit.HOURS) {
                text = (num = trim(trim, (double)time / 3600000.0D)) + " Hour";
            } else if (type == TimeUnit.MINUTES) {
                text = (num = trim(trim, (double)time / 60000.0D)) + " Minute";
            } else if (type == TimeUnit.SECONDS) {
                text = (num = trim(trim, (double)time / 1000.0D)) + " Second";
            } else {
                text = (int)(num = (double)((int)trim(0, (double)time))) + " Millisecond";
            }

            if (num != 1.0D) {
                text = text + "s";
            }

            return text;
        }
    }

    public static double trim(int degree, double d) {
        String format = "#.#";

        for(int i = 1; i < degree; ++i) {
            format = format + "#";
        }

        DecimalFormatSymbols symb = new DecimalFormatSymbols(Locale.US);
        DecimalFormat twoDForm = new DecimalFormat(format, symb);
        return Double.parseDouble(twoDForm.format(d));
    }
}
