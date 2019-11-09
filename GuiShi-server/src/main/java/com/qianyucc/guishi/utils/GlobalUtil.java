package com.qianyucc.guishi.utils;

import cn.hutool.core.date.*;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;

import static com.qianyucc.guishi.config.Constant.DATE_PATTERN;
import static com.qianyucc.guishi.config.Constant.TIME_PATTERN;

/**
 * @author lijing
 * @date 2019-10-19 10:41
 * @description 全局工具类
 */
public class GlobalUtil {
    /**
     * java8格式化日期
     *
     * @param millis
     * @param pattern
     * @return
     */
    public static String formatDate(Long millis, String pattern) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        String format = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return format;
    }
    public static Long parseDate(String date, String pattern) {
        DateTime dateTime = DateUtil.parse(date, pattern);
        return dateTime.toInstant().toEpochMilli();
    }
    public static Long getCurrentTimeMillions(){
        String currentTimeStr = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN));
        return parseDate(currentTimeStr, TIME_PATTERN);
    }
    public static Long getCurrentDateMillions(){
        String currentDateStr = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        return parseDate(currentDateStr, DATE_PATTERN);
    }
}
