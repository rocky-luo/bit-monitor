package com.rocky.bit.monitor.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by rocky on 17/8/1.
 */
public class DateUtil {
    private final static DateTimeFormatter COMMON_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public static Date beginOfDay(Date date) {
        return new DateTime(date.getTime()).withTimeAtStartOfDay().toDate();
    }

    public static Date endOfDay(Date date) {
        long endTime = new DateTime(date.getTime()).plusDays(1).withTimeAtStartOfDay().minusSeconds(1).getMillis();
        return new Date(endTime);
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss将mills对应的日期格式化
     * @param mills
     * @return
     */
    public static String format(long mills) {
        return COMMON_FORMATTER.print(mills);
    }

    public static String format(Date date) {
        return COMMON_FORMATTER.print(date.getTime());
    }

}
