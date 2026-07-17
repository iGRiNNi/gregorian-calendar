import application.factory.CachedCalendarTemplateFactory;
import application.factory.CalendarTemplateFactory;
import application.service.CalendarService;
import application.service.DefaultCalendarService;
import presentation.CalendarRenderer;
import presentation.console.ConsoleCalendarApplication;
import presentation.console.ConsoleCalendarRenderer;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        final CalendarTemplateFactory templateFactory = new CachedCalendarTemplateFactory();

        final CalendarService calendarService = new DefaultCalendarService(templateFactory);

        final CalendarRenderer calendarRenderer = new ConsoleCalendarRenderer();

        final ConsoleCalendarApplication application =
                new ConsoleCalendarApplication(
                        calendarService,
                        calendarRenderer,
                        System.in,
                        System.out,
                        System.err);

        application.run();
    }
}
