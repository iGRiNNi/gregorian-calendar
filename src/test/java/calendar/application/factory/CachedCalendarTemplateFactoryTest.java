package calendar.application.factory;

import application.factory.CachedCalendarTemplateFactory;
import application.factory.CalendarTemplateFactory;
import domain.CalendarTemplate;
import domain.CalendarType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class CachedCalendarTemplateFactoryTest {

    private final CalendarTemplateFactory factory = new CachedCalendarTemplateFactory();

    @Test
    void getTemplate_whenYearIsValid_shouldReturnExpectedTemplate() {
        // Arrange
        final int validTestYear = 2024;
        Year testYear = Year.of(validTestYear);
        CalendarType expectedCalendarType = CalendarType.LEAP_MONDAY;

        // Act
        CalendarTemplate template = factory.getTemplate(testYear);

        // Assert
        assertEquals(expectedCalendarType, template.type());
    }

    @Test
    void getTemplate_whenYearsHaveSameCalendarType_shouldReturnSameTemplate() {
        // Arrange
        Year firstTestYear = Year.of(2026);
        Year secondTestYear = Year.of(2015);

        // Act
        CalendarTemplate firstTemplate = factory.getTemplate(firstTestYear);

        CalendarTemplate secondTemplate = factory.getTemplate(secondTestYear);

        // Assert
        assertSame(firstTemplate, secondTemplate);
    }

    @Test
    void getTemplate_whenYearsHaveDifferentCalendarTypes_shouldReturnDifferentTemplates() {
        // Arrange
        final Year firstTestYear = Year.of(2024);
        final Year secondTestYear = Year.of(2025);

        // Act
        CalendarTemplate firstTemplate = factory.getTemplate(firstTestYear);

        CalendarTemplate secondTemplate = factory.getTemplate(secondTestYear);

        // Assert
        assertNotSame(firstTemplate, secondTemplate);
    }

    @ParameterizedTest
    @CsvSource({
            "2024, LEAP_MONDAY",
            "2025, NORMAL_WEDNESDAY",
            "2026, NORMAL_THURSDAY"
    })
    void getTemplate_whenYearIsValid_shouldReturnTemplateWithExpectedType(int testYearValue, CalendarType expectedType) {
        // Arrange
        final Year testYear = Year.of(testYearValue);

        // Act
        final CalendarTemplate template = factory.getTemplate(testYear);

        // Assert
        assertEquals(expectedType, template.type());
    }
}
