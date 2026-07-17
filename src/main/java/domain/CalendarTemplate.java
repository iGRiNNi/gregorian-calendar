package domain;

import domain.exception.InvalidCalendarTemplateException;

import java.time.DayOfWeek;
import java.time.Month;
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

        validateMonthsOrder(months);
        validateFebruaryNumberOfDays(type, months);
        validateFirstDaysOfWeek(type, months);

        this.months = List.copyOf(months);
        this.type = type;
    }

    private static void validateMonthsOrder(List<MonthTemplate> months) {
        Month[] expectedMonths = Month.values();

        for (int index = 0; index < expectedMonths.length; index++) {
            Month actualMonth = months.get(index).month();
            Month expectedMonth = expectedMonths[index];

            if (actualMonth != expectedMonth) {
                throw new InvalidCalendarTemplateException("Expected month %s at index %d, but was %s"
                                .formatted(
                                        expectedMonth,
                                        index,
                                        actualMonth
                                )
                );
            }
        }
    }

    private static void validateFebruaryNumberOfDays(CalendarType type, List<MonthTemplate> months) {
        final int februaryIndex = Month.FEBRUARY.getValue() - 1;
        final MonthTemplate february = months.get(februaryIndex);
        final int expectedNumberOfDays = Month.FEBRUARY.length(type.isLeap());

        if (february.numberOfDays() != expectedNumberOfDays) {
            throw new InvalidCalendarTemplateException("February must contain %d days for calendar type %s"
                            .formatted(
                                    expectedNumberOfDays,
                                    type
                            )
            );
        }
    }

    private static void validateFirstDaysOfWeek(CalendarType type, List<MonthTemplate> months) {
        DayOfWeek expectedFirstDayOfWeek = type.getFirstDayOfYear();

        for (MonthTemplate monthTemplate : months) {
            if (monthTemplate.firstDayOfWeek() != expectedFirstDayOfWeek) {
                throw new InvalidCalendarTemplateException("Month %s must start on %s"
                                .formatted(
                                        monthTemplate.month(),
                                        expectedFirstDayOfWeek
                                )
                );
            }

            expectedFirstDayOfWeek = expectedFirstDayOfWeek.plus(monthTemplate.numberOfDays() % 7);
        }
    }
}
