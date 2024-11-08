package org.hncdev.usom.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class DateTimeConverterTest {

    private DateTimeConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DateTimeConverter();
    }

    @Test
    void shouldConvertValidDateTime() {
        String dateStr = "2024-01-01 12:34:56";

        LocalDateTime result = converter.convert(dateStr);

        assertThat(result)
                .isNotNull()
                .satisfies(dt -> {
                    assertThat(dt.getYear()).isEqualTo(2024);
                    assertThat(dt.getMonthValue()).isEqualTo(1);
                    assertThat(dt.getDayOfMonth()).isEqualTo(1);
                    assertThat(dt.getHour()).isEqualTo(12);
                    assertThat(dt.getMinute()).isEqualTo(34);
                    assertThat(dt.getSecond()).isEqualTo(56);
                });
    }

    @Test
    void shouldConvertDateTimeWithMilliseconds() {
        // given
        String dateStr = "2024-01-01 12:34:56.789";

        // when
        LocalDateTime result = converter.convert(dateStr);

        // then
        assertThat(result)
                .isNotNull()
                .satisfies(dt -> {
                    assertThat(dt.getYear()).isEqualTo(2024);
                    assertThat(dt.getMonthValue()).isEqualTo(1);
                    assertThat(dt.getDayOfMonth()).isEqualTo(1);
                    assertThat(dt.getHour()).isEqualTo(12);
                    assertThat(dt.getMinute()).isEqualTo(34);
                    assertThat(dt.getSecond()).isEqualTo(56);
                    assertThat(dt.getNano()).isZero(); // Milliseconds should be truncated
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2024/01/01 12:34:56",    // Wrong date separator
            "2024-01-01 12.34.56",    // Wrong time separator
            "2024-01-01",             // Missing time
            "12:34:56",               // Missing date
            "2024-13-01 12:34:56",    // Invalid month
            "2024-01-32 12:34:56",    // Invalid day
            "2024-01-01 25:34:56",    // Invalid hour
            "2024-01-01 12:60:56",    // Invalid minute
            "2024-01-01 12:34:61",    // Invalid second
            "01-01-2024 12:34:56",    // Wrong date order
            "2024-01-01 12:34",       // Incomplete time
            "invalid"                  // Completely invalid format
    })
    void shouldThrowExceptionForInvalidFormats(String invalidDate) {
        assertThatThrownBy(() -> converter.convert(invalidDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expected format");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void shouldThrowExceptionForNullOrEmptyInput(String invalidInput) {
        assertThatThrownBy(() -> converter.convert(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(invalidInput == null ? "null" : "empty");
    }

    @Test
    void shouldValidateValidDatetime() {
        String validDate = "2024-01-01 12:34:56";

        assertThat(converter.isValid(validDate)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2024/01/01 12:34:56",
            "2024-01-01 12.34.56",
            "invalid",
            "2024-01-01",
            "12:34:56"
    })
    void shouldReturnFalseForInvalidFormats(String invalidDate) {
        assertThat(converter.isValid(invalidDate)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnFalseForNullOrEmptyInput(String invalidInput) {
        assertThat(converter.isValid(invalidInput)).isFalse();
    }

    @Test
    void shouldHandleWhitespace() {
        String dateWithWhitespace = "  2024-01-01 12:34:56  ";

        LocalDateTime result = converter.convert(dateWithWhitespace);

        assertThat(result)
                .isNotNull()
                .satisfies(dt -> {
                    assertThat(dt.getYear()).isEqualTo(2024);
                    assertThat(dt.getMonthValue()).isEqualTo(1);
                    assertThat(dt.getDayOfMonth()).isEqualTo(1);
                    assertThat(dt.getHour()).isEqualTo(12);
                    assertThat(dt.getMinute()).isEqualTo(34);
                    assertThat(dt.getSecond()).isEqualTo(56);
                });
    }
}