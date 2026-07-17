package domain;

import domain.exception.InvalidMonthTemplateException;

import java.time.*;

public record MonthTemplate(Month month, int numberOfDays, DayOfWeek firstDayOfWeek) {

    public MonthTemplate(Month month, int numberOfDays, DayOfWeek firstDayOfWeek) {

        if (month == null) {
            throw new InvalidMonthTemplateException("Month must not be null");
        }

        if (firstDayOfWeek == null) {
            throw new InvalidMonthTemplateException("First day of month must not be null");
        }

        if (!isValidNumberOfDays(month, numberOfDays)) {
            throw new InvalidMonthTemplateException("Invalid number of days %d for month %s"
                            .formatted(numberOfDays, month));
        }

        this.month = month;
        this.numberOfDays = numberOfDays;
        this.firstDayOfWeek = firstDayOfWeek;
    }

    private static boolean isValidNumberOfDays(Month month, int numberOfDays) {
        return switch (month) {
            case FEBRUARY ->
                    numberOfDays == 28 || numberOfDays == 29;

            case APRIL, JUNE, SEPTEMBER, NOVEMBER ->
                    numberOfDays == 30;

            default ->
                    numberOfDays == 31;
        };
    }
}
