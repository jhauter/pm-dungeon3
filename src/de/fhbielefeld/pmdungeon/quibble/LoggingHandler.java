package de.fhbielefeld.pmdungeon.quibble;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 
 * @author Malte Kanders
 * @author Andreas Wegner
 * @author Mathis Grabert
 * 
 */
public class LoggingHandler {

	private static final int MAX_LOG_FILES = 10;
	public static final Logger logger = Logger.getLogger(LoggingHandler.class.getName());
	private static FileHandler fh;
	private static DateTimeFormatter dtFormatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	private static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

	private static Handler handler = new ConsoleHandler();

	static {
		handler.setLevel(Level.FINEST);
		handler.setFormatter(MyFormatter());

		logger.setUseParentHandlers(false);
		logger.addHandler(handler);

		savedata();
	}

	private LoggingHandler() {

	}

	private static void savedata() {

		clearOldLogFiles();
		try {
			File file;
			file = new File(date.format(new java.util.Date()) + ".log");
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
	
	private static void clearOldLogFiles() {
		List<File> logFiles = new ArrayList<>();
		File[] files = new File(".").listFiles();
		for(int i = 0; i< files.length; i++) {
			if(files[i].getName().endsWith(".log")) {
				logFiles.add(files[i]);		
				
			}
		}
		logFiles.sort((File o1, File o2)-> {
			String dateStr1 = o1.getName().substring(0,o1.getName().indexOf(".log"));
			String dateStr2 = o2.getName().substring(0,o2.getName().indexOf(".log"));
			
			Date date1;
			Date date2;
			try {
				date1 = date.parse(dateStr1);
				
			}
			catch (ParseException e) {
				return 1;
			}
			try {
				date2 = date.parse(dateStr2);
			}
			catch (ParseException e) {
				return -1;
			}
			return date1.compareTo(date2);
		});
		
		for(int i = MAX_LOG_FILES-1; i <logFiles.size(); i++) {
			logFiles.get(i).delete();
		}
	}
	
	
}


