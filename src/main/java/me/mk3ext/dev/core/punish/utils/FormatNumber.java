package me.mk3ext.dev.core.punish.utils;

public class FormatNumber {

    public static String format(int i) {
        if (i == -1) {
            return "Permanent";
        } else {
            switch (i) {
                case 1:
                    return "1 Hour";
                case 2:
                    return "2 Hours";
                case 4:
                    return "4 Hours";
                case 8:
                    return "8 Hours";
                case 12:
                    return "12 Hours";
                case 16:
                    return "16 Hours";
                case 24:
                    return "1 Day";
                case 32:
                    return "1 Day, 8 Hours";
                case 72:
                    return "3 Days";
                case 168:
                    return "7 Days";
                case 336:
                    return "14 Days";
                case 720:
                    return "30 Days";

            }
        }
        return "1 Hour";
    }

}
