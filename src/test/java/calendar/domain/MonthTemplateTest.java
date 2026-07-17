package calendar.domain;

import domain.MonthTemplate;
import domain.exception.InvalidMonthTemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class MonthTemplateTest {

    @Test
    void constructor_whenValidParams_shouldCreateValidMonthTemplate() {
        // Arrange
        final Month testMonth = Month.JANUARY;
        final int testNumberOfDays = 31;
        final DayOfWeek testDayOfWeek = DayOfWeek.MONDAY;

        // Act
        MonthTemplate template = new MonthTemplate(
                testMonth,
                testNumberOfDays,
                testDayOfWeek
        );

        // Assert
        assertEquals(testMonth, template.month());
        assertEquals(testNumberOfDays, template.numberOfDays());
        assertEquals(testDayOfWeek, template.firstDayOfWeek());
    }

    @Test
    void constructor_whenMonthIsNull_shouldThrowInvalidMonthTemplateException() {
        // Arrange
        final Month testMonth = null;
        final int testNumberOfDays = 31;
        final DayOfWeek testDayOfWeek = DayOfWeek.MONDAY;

        // Act + Assert
        assertThrows(
                InvalidMonthTemplateException.class,
                () -> new MonthTemplate(
                        testMonth,
                        testNumberOfDays,
                        testDayOfWeek
                ));
    }

    @Test
    void constructor_whenFirstDayOfWeekIsNull_shouldThrowInvalidMonthTemplateException() {
        // Arrange
        final Month testMonth = Month.JANUARY;
        final int testNumberOfDays = 31;
        final DayOfWeek testDayOfWeek = null;

        // Act + Assert
        assertThrows(
                InvalidMonthTemplateException.class,
                () -> new MonthTemplate(
                        testMonth,
                        testNumberOfDays,
                        testDayOfWeek
                )
        );
    }

    @ParameterizedTest(name = "{0} with {1} days should be rejected")
    @CsvSource({
            "JANUARY, 32",
            "JANUARY, 30",
            "JANUARY, 0",
            "JANUARY, -1",
            "APRIL, 31",
            "APRIL, 29",
            "APRIL, 32",
            "APRIL, 100",
            "FEBRUARY, 30",
            "FEBRUARY, 27"
    })
    void constructor_whenNumberOfDaysIsInvalid_shouldThrowInvalidMonthTemplateException(Month testMonth, int testNumberOfDays) {
        // Arrange
        final DayOfWeek testDayOfWeek = DayOfWeek.MONDAY;

        // Act + Assert
        assertThrows(
                InvalidMonthTemplateException.class,
                () -> new MonthTemplate(
                        testMonth,
                        testNumberOfDays,
                        testDayOfWeek
                )
        );
    }

    @ParameterizedTest
    @CsvSource({
            "JANUARY, 31",
            "FEBRUARY, 28",
            "FEBRUARY, 29",
            "MARCH, 31",
            "APRIL, 30",
            "MAY, 31",
            "JUNE, 30",
            "JULY, 31",
            "AUGUST, 31",
            "SEPTEMBER, 30",
            "OCTOBER, 31",
            "NOVEMBER, 30",
            "DECEMBER, 31"
    })
    void constructor_whenNumberOfDaysIsValid_shouldCreateValidMonthTemplate(Month testMonth, int testNumberOfDays) {
        // Arrange
        final DayOfWeek testDayOfWeek = DayOfWeek.MONDAY;

        // Act
        final MonthTemplate template = new MonthTemplate(
                testMonth,
                testNumberOfDays,
                testDayOfWeek
        );

        // Assert
        assertEquals(testMonth, template.month());
        assertEquals(testNumberOfDays, template.numberOfDays());
        assertEquals(testDayOfWeek, template.firstDayOfWeek());
    }
}
