package domain;

import domain.exception.InvalidYearCalendarException;

import java.time.Year;

public record YearCalendar(Year year, CalendarTemplate template) {

    private static final Year MIN_YEAR = Year.of(1600);

    public YearCalendar(Year year, CalendarTemplate template) {
        if (year == null) {
            throw new InvalidYearCalendarException("Year must not be null");
        }

        if (template == null) {
            throw new InvalidYearCalendarException("Calendar template must not be null");
        }

        if (year.compareTo(MIN_YEAR) < 0 || year.compareTo(MIN_YEAR) == 0) {
            throw new InvalidYearCalendarException("Year must be greater than 1600");
        }

        CalendarType expectedType = CalendarType.fromYear(year);

        if (template.type() != expectedType) {
            throw new InvalidYearCalendarException("Calendar template does not match year " + year);
        }

        this.template = template;
        this.year = year;
    }
}
