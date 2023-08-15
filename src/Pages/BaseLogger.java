package Pages;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class BaseLogger {
	private Logger logger;
	private static Handler fileHandler;

	private BaseLogger(String filename, Level level) throws IOException {

		if (fileHandler == null) {
			fileHandler = new FileHandler(filename, true); 
			fileHandler.setFormatter(new SimpleFormatter());
		}

		logger = Logger.getLogger(filename);
		logger.addHandler(fileHandler);
		logger.setLevel(level);
	    logger.setUseParentHandlers(false); // Disable the default console handler
	}

	public void log(String message) {
		logger.log(Level.INFO, message);
	}
    public void logError(String errorMessage) {
        logger.log(Level.SEVERE, errorMessage);
    }

	public static BaseLogger info() throws IOException {
		return new BaseLogger("application_log.txt", Level.INFO);
	}

	public static BaseLogger error() throws IOException {
		return new BaseLogger("application_error.txt", Level.SEVERE);
	}
}
