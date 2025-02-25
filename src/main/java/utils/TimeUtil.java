package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {
    public static long parseTimeToSeconds(String time) {
        Pattern pattern = Pattern.compile("(\\d+)([smhd])");
        Matcher matcher = pattern.matcher(time);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid time format.");
        }

        int value = Integer.parseInt(matcher.group(1));
        String unit = matcher.group(2);

        switch (unit) {
            case "s":
                return value;
            case "m":
                return value * 60;
            case "h":
                return value * 3600;
            case "d":
                return value * 86400;
            default:
                throw new IllegalArgumentException("Invalid time unit.");
        }
    }

    public static String formatTime(long seconds) {
        long minutes = seconds / 60;
        long hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;

        if (hours > 0) {
            return hours + "h " + minutes + "m";
        } else if (minutes > 0) {
            return minutes + "m " + seconds + "s";
        } else {
            return seconds + "s";
        }
    }
}
