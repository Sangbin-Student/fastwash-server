package kr.hs.dgsw.fastwash.fastwashserver.global.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalTime timeFromString(String value) {
        return LocalTime.parse(String.format("%s:00", value), DateTimeFormatter.ofPattern("H:mm:ss"));
    }
}
