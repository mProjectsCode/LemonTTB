package LemonTTB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;

import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;

/**
 * A class for printing fancy log messages into the console.
 * 
 * @author Moritz Jung
 */
public class Logger {
    /**
     * An enum describing the level of the log outout.
     */
    private enum Level {
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
        DEBUG,
        /**
         * Represents a log for a discord command.
         */
        COMMAND
    }

    /**
     * Wether debug logs should be active.
     */
    public static boolean debug;

    /**
     * 
     */
    public static String logFilePath;

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
     * Instance of gson.
     */
    private Gson gson;

    /**
     * The constructor for Logger.
     * Also calcualtes longestLogLevelString.
     * 
     * @param debug
     * @param obj
     */
    public Logger(Class<?> obj) {
        // Calculate the longest (in terms of characters) LogLevel
        Level longestLogLevel = Level.INFO;
        for (Level logLevel : Level.values()) {
            if (logLevel.toString().length() > longestLogLevel.toString().length()) {
                longestLogLevel = logLevel;
            }
        }
        longestLogLevelString = longestLogLevel.toString().length();
        this.obj = obj;
        this.gson = new Gson();
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
        log(Level.INFO, message);
    }

    /**
     * Logs a warning.
     * 
     * @param message
     */
    public void logWarning(String message) {
        log(Level.WARNING, message);
    }

    /**
     * Logs an error message.
     * 
     * @param message
     */
    public void logError(String message) {
        log(Level.ERROR, message);
    }

    /**
     * Logs an error.
     * 
     * @param e
     */
    public void logError(Exception e) {
        log(Level.ERROR, e.getMessage());
        e.printStackTrace();
    }

    /**
     * Logs debug messages. Disabled if debug == false.
     * 
     * @param message
     */
    public void logDebug(String message) {
        if (debug) {
            log(Level.DEBUG, message);
        }
    }

    /**
     * Logs a discord command.
     * 
     * @param commandObject
     * @param msg
     */
    public void logCommand(CommandObject commandObject, Message msg) {
        StringBuilder sb = new StringBuilder();

        sb.append(gson.toJson(commandObject));
        sb.append(" from ");
        sb.append(msg.getAuthor().getAsMention());
        sb.append(". origional message: \"");
        sb.append(msg.getContentRaw());
        sb.append("\"");

        log(Level.COMMAND, sb.toString());
    }

    /**
     * Logs a discord command.
     * 
     * @param commandObject
     * @param success
     * @param message
     */
    public void logCommand(CommandObject commandObject, boolean success, String message) {
        StringBuilder sb = new StringBuilder();

        sb.append(commandObject.id);
        sb.append(success ? " SUCCESS " : " FAILED ");
        sb.append(message);

        log(Level.COMMAND, sb.toString());
    }

    /**
     * Logs a discord command.
     * 
     * @param commandObject
     * @param success
     * @param e
     */
    public void logCommand(CommandObject commandObject, boolean success, Exception e) {
        StringBuilder sb = new StringBuilder();

        sb.append(commandObject.id);
        sb.append(success ? " SUCCESS " : " FAILED ");
        sb.append(e.getMessage());

        log(Level.COMMAND, sb.toString());

        if (debug) {
            e.printStackTrace();
        }
    }

    /**
     * Print a fancy log message in the console
     * 
     * @param logLevel
     * @param message
     */
    public void log(Level logLevel, String message) {
        log(logLevel, message, true);
    }

    /**
     * Print a fancy log message in the console
     * 
     * @param logLevel
     * @param message
     */
    public void log(Level logLevel, String message, boolean writeToFile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(constructHeadder(logLevel));
        stringBuilder.append(" ");
        stringBuilder.append(message);

        System.out.println(stringBuilder.toString());
        if (writeToFile) {
            try {
                logToFile(stringBuilder.toString());
            } catch (IOException e) {
                log(Level.ERROR, "Could not write to log file. " + e.getMessage(), false);
            }
        }
    }

    /**
     * Construct a header with the LogLevel and the time like this:
     * [LogLevel][HH:mm:ss]
     * 
     * @param logLevel
     * @return String
     */
    private String constructHeadder(Level logLevel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(logLevel.name());
        stringBuilder.append("]");

        stringBuilder.append(calculateSpacing(logLevel));

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
    private String calculateSpacing(Level logLevel) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < longestLogLevelString - logLevel.toString().length(); i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * @param line
     * @throws IOException
     */
    private void logToFile(String line) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDateTime localDateTime = LocalDateTime.now();
        File file = new File(logFilePath, "/logger/" + dateTimeFormatter.format(localDateTime) + ".txt");
        File dir = file.getParentFile();

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Could not create parent directories");
            }
        } else if (!dir.isDirectory()) {
            throw new IOException("The parent file is not a directory");
        }

        try (Writer writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(line + System.lineSeparator());
            writer.close();
        } catch (Exception e) {
            throw new IOException("Could not open the log file");
        }
    }
}
