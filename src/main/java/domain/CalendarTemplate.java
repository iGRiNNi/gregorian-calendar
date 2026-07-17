package domain;

import domain.exception.InvalidCalendarTemplateException;

import java.util.List;
import java.util.Objects;

public record CalendarTemplate(CalendarType type, List<MonthTemplate> months) {

    private static final int MONTHS_IN_YEAR = 12;

    public CalendarTemplate(CalendarType type, List<MonthTemplate> months) {
        if (type == null) {
            throw new InvalidCalendarTemplateException("Calendar type must not be null");
        }

        if (months == null) {
            throw new InvalidCalendarTemplateException("Months must not be null");
        }

        if (months.size() != MONTHS_IN_YEAR) {
            throw new InvalidCalendarTemplateException("Calendar must contain exactly 12 months");
        }

        if (months.stream().anyMatch(Objects::isNull)) {
            throw new InvalidCalendarTemplateException("Calendar months must not contain null values");
        }

        this.months = List.copyOf(months);
        this.type = type;
    }
}
