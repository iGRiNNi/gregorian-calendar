package domain;

import java.time.DayOfWeek;
import java.time.Year;
import java.util.Arrays;
import java.util.Objects;

public enum CalendarType {

    NORMAL_MONDAY(false, DayOfWeek.MONDAY),
    NORMAL_TUESDAY(false, DayOfWeek.TUESDAY),
    NORMAL_WEDNESDAY(false, DayOfWeek.WEDNESDAY),
    NORMAL_THURSDAY(false, DayOfWeek.THURSDAY),
    NORMAL_FRIDAY(false, DayOfWeek.FRIDAY),
    NORMAL_SATURDAY(false, DayOfWeek.SATURDAY),
    NORMAL_SUNDAY(false, DayOfWeek.SUNDAY),

    LEAP_MONDAY(true, DayOfWeek.MONDAY),
    LEAP_TUESDAY(true, DayOfWeek.TUESDAY),
    LEAP_WEDNESDAY(true, DayOfWeek.WEDNESDAY),
    LEAP_THURSDAY(true, DayOfWeek.THURSDAY),
    LEAP_FRIDAY(true, DayOfWeek.FRIDAY),
    LEAP_SATURDAY(true, DayOfWeek.SATURDAY),
    LEAP_SUNDAY(true, DayOfWeek.SUNDAY);

    private final boolean leap;

    private final DayOfWeek firstDayOfYear;

    private CalendarType(boolean isLeap, DayOfWeek firstDayOfYear) {
        this.leap = isLeap;
        this.firstDayOfYear = firstDayOfYear;
    }

    public boolean isLeap() {
        return leap;
    }

    public DayOfWeek getFirstDayOfYear() {
        return firstDayOfYear;
    }

    public static CalendarType fromYear(Year year) {
        Objects.requireNonNull(year, "year must not be null");

        boolean leap = year.isLeap();
        DayOfWeek firstDay = year.atDay(1).getDayOfWeek();

        for (CalendarType type : values()) {
            if (type.leap == leap && type.firstDayOfYear == firstDay) {
                return type;
            }
        }

        throw new IllegalStateException("Calendar type not found for year " + year);
    }
}
