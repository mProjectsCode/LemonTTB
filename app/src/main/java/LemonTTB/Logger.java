package LemonTTB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class for printing fancy log messages into the console.
 * 
 * @author Moritz Jung
 */
public class Logger {
    /**
     * An enum describing the level of the log outout.
     */
    private enum LogLevel {
        /**
         * Represents an info level log.
         */
        INFO,
        /**
         * Represents a warning level log.
         */
        WARNING,
        /**
         * Represents an error level log.
         */
        ERROR,
        /**
         * Represents a debug level log. Disabled if debug == false.
         */
        DEBUG
    }

    /**
     * The class to log for.
     */
    private Class<?> obj;

    /**
     * Describes the length of the loglevel with the most characters. Used for
     * aligning the timestamps.
     */
    private int longestLogLevelString;

    /**
     * Wether debug logs should be active
     */
    public static boolean debug;

    /**
     * The constructor for Logger.
     * Also calcualtes longestLogLevelString.
     * 
     * @param debug
     * @param obj
     */
    public Logger(Class<?> obj) {
        // Calculate the longest (in terms of characters) LogLevel
        LogLevel longestLogLevel = LogLevel.INFO;
        for (LogLevel logLevel : LogLevel.values()) {
            if (logLevel.toString().length() > longestLogLevel.toString().length()) {
                longestLogLevel = logLevel;
            }
        }
        longestLogLevelString = longestLogLevel.toString().length();
        this.obj = obj;
    }

    /**
     * Returns a logger object.
     * 
     * @param obj
     * @return Logger
     */
    public static Logger getLogger(Class<?> obj) {
        return new Logger(obj);
    }

    /**
     * Logs an info.
     * 
     * @param message
     */
    public void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Logs a warning.
     * 
     * @param message
     */
    public void logWarning(String message) {
        log(LogLevel.WARNING, message);
    }

    /**
     * Logs an error message.
     * 
     * @param message
     */
    public void logError(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Logs an error.
     * 
     * @param e
     */
    public void logError(Exception e) {
        log(LogLevel.ERROR, e.getMessage());
        e.printStackTrace();
    }

    /**
     * Logs debug messages. Disabled if debug == false.
     * 
     * @param message
     */
    public void logDebug(String message) {
        if (debug) {
            log(LogLevel.DEBUG, message);
        }
    }

    /**
     * Print a fancy log message in the console
     * 
     * @param logLevel
     * @param message
     */
    public void log(LogLevel logLevel, String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(constructHeadder(logLevel));
        stringBuilder.append(calculateSpacing(logLevel));
        stringBuilder.append(message);

        System.out.println(stringBuilder.toString());
    }

    /**
     * Construct a header with the LogLevel and the time like this:
     * [LogLevel][HH:mm:ss]
     * 
     * @param logLevel
     * @return String
     */
    private String constructHeadder(LogLevel logLevel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(logLevel.name());
        stringBuilder.append("]");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        stringBuilder.append("[");
        stringBuilder.append(dateTimeFormatter.format(localDateTime));
        stringBuilder.append("]");

        stringBuilder.append("[");
        stringBuilder.append(obj.getName());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    /**
     * Calculate spacing after the log header based on the longest LogLevel
     * 
     * @param logLevel
     * @return String
     */
    private String calculateSpacing(LogLevel logLevel) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < longestLogLevelString - logLevel.toString().length() + 1; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
