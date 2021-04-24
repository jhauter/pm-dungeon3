package de.fhbielefeld.pmdungeon.quibble;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggingHandler {

	// author Malte Kanders
	public static final Logger logger = Logger.getLogger(LoggingHandler.class.getName());
//	private static final String classPath = "de.fhbielefeld.pmdungeon.quibble.";
	private static FileHandler fh;
	private static DateTimeFormatter dtFormatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

	private static Handler handler = new ConsoleHandler();
	
	static
	{
		handler.setLevel(Level.FINEST);
		handler.setFormatter(MyFormatter());

		logger.setUseParentHandlers(false);
		logger.addHandler(handler);

		savedata();
	}

	/**
	 * Initializes the LoggingHandler. <br>
	 * The class owns a static logger of type java.util.logging.Logger. </br>
	 * 
	 * <br>
	 * Formatted texts are as follows: </br>
	 * 
	 * <code>Logger: <strong>.LoggingHandler</strong> in <strong>SourceClass</strong> 
	 * <br><code>Level</code>: Message </br>
	 * Date and time
	 * 
	 * @Format: �dd.MM.yyyy HH:mm:ss�
	 */
	private LoggingHandler() {
//		handler.setLevel(Level.FINEST);
//		handler.setFormatter(MyFormatter());
//
//		logger.setUseParentHandlers(false);
//		logger.addHandler(handler);
//
//		savedata();
	}

	private static void savedata() {
		File file = new File("log.txt");
		try {
			if (!(file.exists())) {
				file.createNewFile();
			}
			logger.setLevel(Level.ALL);
			fh = new FileHandler(file.toString());
			fh.setFormatter(MyFormatter());
			logger.addHandler(fh);
		} catch (SecurityException | IOException e) {
			logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}

	}

	private static Formatter MyFormatter() {

		Formatter f = new Formatter() {

			@Override
			public String format(LogRecord record) {

				String date = formatMillis(record.getMillis());
				String className = record.getSourceClassName();

				String topString = record.getLoggerName() + " in "
						+ className.substring(className.lastIndexOf('.') + 1);
				String middleString = record.getLevel() + ": " + record.getMessage();

				return date + " " + topString + "\n" + middleString + "\n";
			}
		};
		return f;
	}

	private static String formatMillis(long millis) {
		Instant instance = java.time.Instant.ofEpochMilli(millis);
		ZonedDateTime zonedDateTime = java.time.ZonedDateTime.ofInstant(instance, java.time.ZoneId.systemDefault());
		return zonedDateTime.format(dtFormatter);
	}
}