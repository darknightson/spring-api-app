package com.app.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class DateTimeUtils {

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
