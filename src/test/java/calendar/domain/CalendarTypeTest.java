package calendar.domain;

import domain.CalendarType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalendarTypeTest {

    @ParameterizedTest
    @CsvSource({
            "1601, NORMAL_MONDAY",
            "1602, NORMAL_TUESDAY",
            "1603, NORMAL_WEDNESDAY",
            "1609, NORMAL_THURSDAY",
            "1610, NORMAL_FRIDAY",
            "1605, NORMAL_SATURDAY",
            "1606, NORMAL_SUNDAY",

            "1624, LEAP_MONDAY",
            "1608, LEAP_TUESDAY",
            "1620, LEAP_WEDNESDAY",
            "1604, LEAP_THURSDAY",
            "1616, LEAP_FRIDAY",
            "1628, LEAP_SATURDAY",
            "1612, LEAP_SUNDAY"
    })
    void fromYear_whenYearIsValid_shouldReturnExpectedCalendarType(int testYearValue, CalendarType expectedCalendarType) {
        // Arrange
        final Year testYear = Year.of(testYearValue);

        // Act
        final CalendarType actualCalendarType = CalendarType.fromYear(testYear);

        // Assert
        assertEquals(expectedCalendarType, actualCalendarType);
    }

    @Test
    void fromYear_whenYearIsNull_shouldThrowNullPointerException() {
        // Arrange
        final Year testYear = null;

        // Act + Assert
        assertThrows(NullPointerException.class, () -> CalendarType.fromYear(testYear));
    }

    @Test
    void values_shouldContainFourteenCalendarTypes() {
        // Arrange
        final int expectedCalendarTypesCount = 14;

        // Act
        final CalendarType[] calendarTypes = CalendarType.values();

        // Assert
        assertEquals(expectedCalendarTypesCount, calendarTypes.length);
    }

    @ParameterizedTest
    @CsvSource({
            "1900, NORMAL_MONDAY",
            "2000, LEAP_SATURDAY"
    })
    void fromYear_whenYearIsCentury_shouldApplyGregorianLeapYearRules(int testYearValue, CalendarType expectedCalendarType) {
        // Arrange
        final Year testYear = Year.of(testYearValue);

        // Act
        final CalendarType actualCalendarType = CalendarType.fromYear(testYear);

        // Assert
        assertEquals(expectedCalendarType, actualCalendarType);
    }
}
