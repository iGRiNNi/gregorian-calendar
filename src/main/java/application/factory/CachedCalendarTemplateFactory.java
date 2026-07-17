package application.factory;

import domain.CalendarTemplate;
import domain.CalendarType;
import domain.MonthTemplate;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class CachedCalendarTemplateFactory implements CalendarTemplateFactory {

    private final Map<CalendarType, CalendarTemplate> templates;

    public CachedCalendarTemplateFactory() {
        this.templates = createTemplates();
    }

    @Override
    public CalendarTemplate getTemplate(Year year) {
        CalendarType calendarType = CalendarType.fromYear(year);

        return getTemplate(calendarType);
    }

    @Override
    public CalendarTemplate getTemplate(CalendarType calendarType) {
        return templates.get(calendarType);
    }

    private Map<CalendarType, CalendarTemplate> createTemplates() {
        Map<CalendarType, CalendarTemplate> result = new EnumMap<>(CalendarType.class);

        for (CalendarType calendarType : CalendarType.values()) {
            result.put(calendarType, createTemplate(calendarType));
        }

        return Map.copyOf(result);
    }

    private CalendarTemplate createTemplate(CalendarType calendarType) {
        List<MonthTemplate> months = new ArrayList<>();

        DayOfWeek firstDayOfWeek = calendarType.getFirstDayOfYear();

        for (Month month : Month.values()) {
            int numberOfDays = month.length(calendarType.isLeap());

            months.add(new MonthTemplate(month, numberOfDays, firstDayOfWeek));

            firstDayOfWeek = firstDayOfWeek.plus(numberOfDays % 7);
        }

        return new CalendarTemplate(calendarType, months);
    }
}
