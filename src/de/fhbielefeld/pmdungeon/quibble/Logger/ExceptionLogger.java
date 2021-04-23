package de.fhbielefeld.pmdungeon.quibble.Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface ExceptionLogger {

	public static void log (Logger logger, Level l, String string, Exception e) {
		logger.log(l, string, e);
	}
}
