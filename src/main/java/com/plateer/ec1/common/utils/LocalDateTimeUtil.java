package com.plateer.ec1.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

    private final static String YEAR_TO_DATE = "yyyyMMdd";
    private final static String YEAR_TO_SECONDS = "yyyyMMddhhmmss";
    private final static String HOUR_TO_SECONDS ="hhmm";

    public static String toStringYearToDate(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(YEAR_TO_DATE));
    }

    public static String toStringYearToSeconds(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(YEAR_TO_SECONDS));
    }

    public static String toStringHourToSeconds(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(HOUR_TO_SECONDS));
    }

}
