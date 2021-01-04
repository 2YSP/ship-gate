package cn.sp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/4
 */
public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

    public static String formatToYYYYMMDDHHmmss(LocalDateTime dateTime){
        return dateTime.format(FORMATTER);
    }


}
