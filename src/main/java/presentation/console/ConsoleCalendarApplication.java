package presentation.console;

import application.service.CalendarService;
import domain.YearCalendar;
import presentation.CalendarRenderer;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.Objects;
import java.util.Scanner;

public final class ConsoleCalendarApplication {

    private final CalendarService calendarService;
    private final CalendarRenderer calendarRenderer;
    private final InputStream input;
    private final PrintStream output;
    private final PrintStream errorOutput;

    public ConsoleCalendarApplication(CalendarService calendarService, CalendarRenderer calendarRenderer, InputStream input, PrintStream output, PrintStream errorOutput) {
        this.calendarService = Objects.requireNonNull(calendarService, "calendarService must not be null");

        this.calendarRenderer = Objects.requireNonNull(calendarRenderer, "calendarRenderer must not be null");

        this.input = Objects.requireNonNull(input, "input must not be null");

        this.output = Objects.requireNonNull(output, "output must not be null");

        this.errorOutput = Objects.requireNonNull(errorOutput, "errorOutput must not be null");
    }

    public void run() {
        final Scanner scanner = new Scanner(input);

        output.print("Введите год после 1600: ");

        if (!scanner.hasNextInt()) {
            errorOutput.println("Ошибка: год должен быть целым числом");
            return;
        }

        try {
            final int yearValue = scanner.nextInt();
            final Year year = Year.of(yearValue);

            final YearCalendar calendar = calendarService.getCalendar(year);

            final String renderedCalendar = calendarRenderer.render(calendar);

            output.println();
            output.println(renderedCalendar);
        } catch (RuntimeException exception) {
            errorOutput.println("Ошибка: " + exception.getMessage());
        }
    }
}
