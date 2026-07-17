package calendar.application.service;

import application.factory.CalendarTemplateFactory;
import application.service.DefaultCalendarService;
import domain.CalendarTemplate;
import domain.CalendarType;
import domain.MonthTemplate;
import domain.YearCalendar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultCalendarServiceTest {

    @Mock
    private CalendarTemplateFactory templateFactory;

    @InjectMocks
    private DefaultCalendarService calendarService;

    @Test
    void getCalendar_whenYearIsValid_shouldReturnValidYearCalendar() {
        // Arrange
        final Year testYear = Year.of(2024);
        final CalendarTemplate testTemplate = createCalendarTemplate(CalendarType.LEAP_MONDAY);

        when(templateFactory.getTemplate(testYear)).thenReturn(testTemplate);

        // Act
        YearCalendar calendar = calendarService.getCalendar(testYear);

        // Assert
        assertEquals(testYear, calendar.year());
        assertSame(testTemplate, calendar.template());

        verify(templateFactory).getTemplate(testYear);
        verifyNoMoreInteractions(templateFactory);
    }

    @Test
    void getCalendar_whenTemplateFactoryThrowsException_shouldPropagateException() {
        // Arrange
        final Year testYear = Year.of(2024);
        final RuntimeException expectedException = new RuntimeException("Template creation failed");

        when(templateFactory.getTemplate(testYear)).thenThrow(expectedException);

        // Act
        RuntimeException actualException = assertThrows(RuntimeException.class, () -> calendarService.getCalendar(testYear));

        // Assert
        assertSame(expectedException, actualException);

        verify(templateFactory).getTemplate(testYear);
        verifyNoMoreInteractions(templateFactory);
    }

    @Test
    void constructor_whenTemplateFactoryIsNull_shouldThrowNullPointerException() {
        // Arrange
        final CalendarTemplateFactory testTemplateFactory = null;

        // Act + Assert
        assertThrows(NullPointerException.class, () -> new DefaultCalendarService(testTemplateFactory));
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
