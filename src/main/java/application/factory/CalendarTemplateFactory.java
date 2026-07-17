package application.factory;

import domain.CalendarTemplate;
import domain.CalendarType;

import java.time.Year;


public interface CalendarTemplateFactory {

    CalendarTemplate getTemplate(Year year);

    CalendarTemplate getTemplate(CalendarType type);
}
