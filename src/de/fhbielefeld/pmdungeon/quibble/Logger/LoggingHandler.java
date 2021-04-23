package de.fhbielefeld.pmdungeon.quibble.Logger;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggingHandler {

	public static final Logger status = Logger.getLogger(LoggingHandler.class.getName());
	private static final String classPath = "de.fhbielefeld.pmdungeon.quibble.";

	Handler handler = new ConsoleHandler();

	public LoggingHandler() {
		handler.setLevel(Level.FINEST);
		handler.setFormatter(MyFormatter());

		status.setUseParentHandlers(false);
		status.addHandler(handler);
	}

	public Formatter MyFormatter() {

		Formatter f = new Formatter() {

			@Override
			public String format(LogRecord record) {
				
				String date = formatMillis(record.getMillis());

				String topString = "Logger: " + record.getLoggerName().replace(classPath, "classPath.") + " in "
						+ record.getSourceClassName().replace(classPath, "classPath.");
				String middleString = record.getLevel() + ": " + record.getMessage();
				
				return String.format(topString + "\n" + middleString + "\n" + date + "\n");
			}
		};
		return f;
	}

//	public static void log(Level l, String msg, String place) {
//		status.setUseParentHandlers(false);
//		status.setLevel(l);
//		status.log(l, msg, place);
//	}

	private String formatMillis(long millis) {
		Instant instance = java.time.Instant.ofEpochMilli(millis);
		ZonedDateTime zonedDateTime = java.time.ZonedDateTime.ofInstant(instance, java.time.ZoneId.of("Europe/Berlin"));
		DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		return zonedDateTime.format(formatter);
	}
}
