package presentation.console;

import domain.MonthTemplate;
import domain.YearCalendar;
import presentation.CalendarRenderer;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public final class ConsoleCalendarRenderer implements CalendarRenderer {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final Locale RUSSIAN_LOCALE = Locale.forLanguageTag("ru");

    private static final String WEEK_HEADER = "Пн Вт Ср Чт Пт Сб Вс";

    @Override
    public String render(YearCalendar calendar) {
        Objects.requireNonNull(calendar, "calendar must not be null");

        StringBuilder result = new StringBuilder();

        for (MonthTemplate monthTemplate : calendar.template().months()) {
            appendMonth(result, calendar, monthTemplate);
        }

        return result.toString();
    }

    private void appendMonth(StringBuilder result, YearCalendar calendar, MonthTemplate monthTemplate) {
        result.append(getMonthName(monthTemplate.month()))
                .append(" ").append(calendar.year()).append(LINE_SEPARATOR);

        result.append(WEEK_HEADER).append(LINE_SEPARATOR);

        appendMonthDays(result, monthTemplate);

        result.append(LINE_SEPARATOR);
    }

    private void appendMonthDays(StringBuilder result, MonthTemplate monthTemplate) {
        int emptyDaysCount = getDayIndex(monthTemplate.firstDayOfWeek());

        result.append("   ".repeat(emptyDaysCount));

        for (int day = 1; day <= monthTemplate.numberOfDays(); day++) {

            result.append("%2d".formatted(day));

            int dayPosition = emptyDaysCount + day;

            if (dayPosition % 7 == 0) {
                result.append(LINE_SEPARATOR);
            } else {
                result.append(" ");
            }
        }

        if ((emptyDaysCount + monthTemplate.numberOfDays()) % 7 != 0) {
            result.append(LINE_SEPARATOR);
        }
    }

    private int getDayIndex(DayOfWeek dayOfWeek) {
        return dayOfWeek.getValue() - 1;
    }

    private String getMonthName(Month month) {
        String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, RUSSIAN_LOCALE);

        return monthName.substring(0, 1).toUpperCase(RUSSIAN_LOCALE) + monthName.substring(1);
    }
}
