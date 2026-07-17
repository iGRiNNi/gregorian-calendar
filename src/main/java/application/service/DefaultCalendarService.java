package application.service;

import application.factory.CalendarTemplateFactory;
import domain.CalendarTemplate;
import domain.YearCalendar;

import java.time.Year;
import java.util.Objects;

public final class DefaultCalendarService implements CalendarService {

    private final CalendarTemplateFactory templateFactory;

    public DefaultCalendarService(CalendarTemplateFactory templateFactory) {
        this.templateFactory = Objects.requireNonNull(templateFactory, "templateFactory must not be null");
    }

    @Override
    public YearCalendar getCalendar(Year year) {
        CalendarTemplate template = templateFactory.getTemplate(year);

        return new YearCalendar(year, template);
    }
}
