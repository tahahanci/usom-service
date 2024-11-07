package org.hncdev.usom.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime convert(String date) {
        if (date.contains(".")) {
            date = date.substring(0, date.indexOf("."));
        }
        return LocalDateTime.parse(date, formatter);
    }
}
