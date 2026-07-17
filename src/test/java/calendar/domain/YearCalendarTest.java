package calendar.domain;

import domain.CalendarTemplate;
import domain.CalendarType;
import domain.MonthTemplate;
import domain.YearCalendar;
import domain.exception.InvalidYearCalendarException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class YearCalendarTest {

    @Test
    void constructor_whenValidParams_shouldCreateValidYearCalendar() {
        // Arrange
        final Year testYear = Year.of(2024);
        final CalendarTemplate testTemplate = createCalendarTemplate(CalendarType.LEAP_MONDAY);

        // Act
        final YearCalendar calendar = new YearCalendar(testYear, testTemplate);

        // Assert
        assertEquals(testYear, calendar.year());
        assertEquals(testTemplate, calendar.template());
    }

    @Test
    void constructor_whenYearIsNull_shouldThrowInvalidYearCalendarException() {
        // Arrange
        final Year testYear = null;
        final CalendarTemplate testTemplate = createCalendarTemplate(CalendarType.NORMAL_MONDAY);

        // Act + Assert
        assertThrows(InvalidYearCalendarException.class, () -> new YearCalendar(testYear, testTemplate));
    }

    @Test
    void constructor_whenTemplateIsNull_shouldThrowInvalidYearCalendarException() {
        // Arrange
        final Year testYear = Year.of(2024);
        final CalendarTemplate testTemplate = null;

        // Act + Assert
        assertThrows(InvalidYearCalendarException.class, () -> new YearCalendar(testYear, testTemplate));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 1599, 1600})
    void constructor_whenYearIsNotGreaterThan1600_shouldThrowInvalidYearCalendarException(int testYearValue) {
        // Arrange
        final Year testYear = Year.of(testYearValue);
        final CalendarType testCalendarType = CalendarType.fromYear(testYear);

        final CalendarTemplate testTemplate = createCalendarTemplate(testCalendarType);

        // Act + Assert
        assertThrows(InvalidYearCalendarException.class, () -> new YearCalendar(testYear, testTemplate));
    }

    @Test
    void constructor_whenYearIs1601_shouldCreateValidYearCalendar() {
        // Arrange
        final Year testYear = Year.of(1601);
        final CalendarType testCalendarType = CalendarType.fromYear(testYear);

        final CalendarTemplate testTemplate = createCalendarTemplate(testCalendarType);

        // Act
        final YearCalendar calendar = new YearCalendar(testYear, testTemplate);

        // Assert
        assertEquals(testYear, calendar.year());
        assertEquals(testTemplate, calendar.template());
    }

    @Test
    void constructor_whenTemplateDoesNotMatchYear_shouldThrowInvalidYearCalendarException() {
        // Arrange
        final Year testYear = Year.of(2024);

        final CalendarTemplate testTemplate = createCalendarTemplate(CalendarType.NORMAL_MONDAY);

        // Act + Assert
        assertThrows(InvalidYearCalendarException.class, () -> new YearCalendar(testYear, testTemplate));
    }

    private CalendarTemplate createCalendarTemplate(CalendarType calendarType) {
        final List<MonthTemplate> months = new ArrayList<>();

        DayOfWeek firstDayOfWeek = calendarType.getFirstDayOfYear();

        for (final Month month : Month.values()) {
            final int numberOfDays = month.length(calendarType.isLeap());

            months.add(new MonthTemplate(month, numberOfDays, firstDayOfWeek));

            firstDayOfWeek = firstDayOfWeek.plus(numberOfDays % 7);
        }

        return new CalendarTemplate(calendarType, months);
    }
}
