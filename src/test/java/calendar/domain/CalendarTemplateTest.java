package calendar.domain;

import domain.CalendarTemplate;
import domain.CalendarType;
import domain.MonthTemplate;
import domain.exception.InvalidCalendarTemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTemplateTest {

    @Test
    void constructor_whenValidParams_shouldCreateValidCalendarTemplate() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = createValidMonths(testType);

        // Act
        final CalendarTemplate template = new CalendarTemplate(testType, testMonths);

        // Assert
        assertEquals(testType, template.type());
        assertEquals(testMonths, template.months());
        assertEquals(12, template.months().size());
    }

    @Test
    void constructor_whenTypeIsNull_shouldThrowInvalidCalendarTemplateException() {
        // Arrange
        final CalendarType testType = null;
        final List<MonthTemplate> testMonths = createValidMonths(CalendarType.NORMAL_MONDAY);

        // Act + Assert
        assertThrows(
                InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths));
    }

    @Test
    void constructor_whenMonthsIsNull_shouldThrowInvalidCalendarTemplateException() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = null;

        // Act + Assert
        assertThrows(
                InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 11, 13, -1})
    void constructor_whenMonthsCountIsNotValid_shouldThrowInvalidCalendarTemplateException(int testMonthsCount) {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> validMonths = createValidMonths(testType);

        final List<MonthTemplate> testMonths = createMonthsWithSize(validMonths, testMonthsCount);

        // Act + Assert
        assertThrows(
                InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths));
    }

    @Test
    void constructor_whenMonthsContainsNull_shouldThrowInvalidCalendarTemplateException() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = new ArrayList<>(createValidMonths(testType));

        testMonths.set(0, null);

        // Act + Assert
        assertThrows(
                InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths));
    }

    @Test
    void constructor_whenSourceListIsChanged_shouldNotChangeCalendarTemplate() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = new ArrayList<>(createValidMonths(testType));

        final CalendarTemplate template = new CalendarTemplate(testType, testMonths);

        // Act
        testMonths.clear();

        // Assert
        assertEquals(12, template.months().size());
    }

    @Test
    void months_whenTryingToModifyList_shouldThrowUnsupportedOperationException() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = createValidMonths(testType);

        final CalendarTemplate template = new CalendarTemplate(testType, testMonths);

        // Act + Assert
        assertThrows(
                UnsupportedOperationException.class,
                () -> template.months().clear());
    }

    @Test
    void constructor_whenMonthsContainDuplicates_shouldThrowInvalidCalendarTemplateException() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = new ArrayList<>(createValidMonths(testType));

        testMonths.set(1, testMonths.get(0));

        // Act + Assert
        assertThrows(
                InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths)
        );
    }

    @Test
    void constructor_whenMonthsOrderIsNotValid_shouldThrowInvalidCalendarTemplateException() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = new ArrayList<>(createValidMonths(testType));

        final MonthTemplate january = testMonths.get(0);
        final MonthTemplate february = testMonths.get(1);

        testMonths.set(0, february);
        testMonths.set(1, january);

        // Act + Assert
        assertThrows(InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths)
        );
    }

    @Test
    void constructor_whenCommonYearFebruaryHasTwentyNineDays_shouldThrowInvalidCalendarTemplateException() {
        // Arrange
        final CalendarType testType = CalendarType.NORMAL_MONDAY;
        final List<MonthTemplate> testMonths = new ArrayList<>(createValidMonths(testType));

        final MonthTemplate february = testMonths.get(1);

        testMonths.set(1, new MonthTemplate(Month.FEBRUARY, 29, february.firstDayOfWeek()));

        // Act + Assert
        assertThrows(
                InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths)
        );
    }

    @Test
    void constructor_whenLeapYearFebruaryHasTwentyEightDays_shouldThrowInvalidCalendarTemplateException() {
        // Arrange
        final CalendarType testType = CalendarType.LEAP_MONDAY;
        final List<MonthTemplate> testMonths = new ArrayList<>(createValidMonths(testType));

        final MonthTemplate february = testMonths.get(1);

        testMonths.set(1, new MonthTemplate(Month.FEBRUARY, 28, february.firstDayOfWeek()));

        // Act + Assert
        assertThrows(
                InvalidCalendarTemplateException.class,
                () -> new CalendarTemplate(testType, testMonths)
        );
    }

    private static List<MonthTemplate> createValidMonths(CalendarType type) {
        final List<MonthTemplate> months = new ArrayList<>();

        DayOfWeek firstDayOfWeek = type.getFirstDayOfYear();

        for (Month month : Month.values()) {
            final int numberOfDays = month.length(type.isLeap());

            months.add(new MonthTemplate(month, numberOfDays, firstDayOfWeek));

            firstDayOfWeek = firstDayOfWeek.plus(numberOfDays % 7);
        }

        return months;
    }

    private static List<MonthTemplate> createMonthsWithSize(List<MonthTemplate> validMonths, int size) {
        final List<MonthTemplate> result = new ArrayList<>();

        for (int index = 0; index < size; index++) {
            result.add(validMonths.get(index % validMonths.size()));
        }

        return result;
    }
}
