package application.service;

import application.factory.CalendarTemplateFactory;
import domain.CalendarTemplate;
import domain.YearCalendar;

import java.time.Year;
import java.util.Objects;

public interface CalendarService {

    YearCalendar getCalendar(Year year);
}
