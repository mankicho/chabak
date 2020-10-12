package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String simpleFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return format.format(date);
    }
}
