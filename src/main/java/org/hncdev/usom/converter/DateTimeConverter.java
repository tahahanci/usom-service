package org.hncdev.usom.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Converts a string date to LocalDateTime.
     * Only accepts dates in format "yyyy-MM-dd HH:mm:ss"
     *
     * @param date The date string to convert
     * @return LocalDateTime representation of the input date
     * @throws IllegalArgumentException if the date string is invalid or in wrong format
     */
    public LocalDateTime convert(String date) {
        if (date == null) {
            throw new IllegalArgumentException("Date string cannot be null");
        }

        String trimmedDate = date.trim();
        if (trimmedDate.isEmpty()) {
            throw new IllegalArgumentException("Date string cannot be empty");
        }

        if (!isValidFormat(trimmedDate)) {
            throw new IllegalArgumentException(
                    String.format("Invalid date format: '%s'. Expected format: yyyy-MM-dd HH:mm:ss", date));
        }

        try {
            if (trimmedDate.contains(".")) {
                trimmedDate = trimmedDate.substring(0, trimmedDate.indexOf("."));
            }

            return LocalDateTime.parse(trimmedDate, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    String.format("Unable to parse date '%s'. Expected format: yyyy-MM-dd HH:mm:ss", date), e);
        }
    }

    private boolean isValidFormat(String date) {
        if (date.contains("/")) {
            return false;
        }

        String[] parts = date.split(" ");
        if (parts.length != 2) {
            return false;
        }

        String datePart = parts[0];
        if (!datePart.contains("-") || datePart.split("-").length != 3) {
            return false;
        }

        String timePart = parts[1];
        if (!timePart.contains(":") || timePart.split(":").length != 3) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the given string can be converted to a valid LocalDateTime.
     *
     * @param date The date string to validate
     * @return true if the date string can be converted, false otherwise
     */
    public boolean isValid(String date) {
        try {
            convert(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}