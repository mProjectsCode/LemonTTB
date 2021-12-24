/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021
 * Programmed by Moritz Jung
 *
 * LemonTTB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LemonTTB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LemonTTB.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.mProjectsCode.LemonTTB.Logger;

import com.google.gson.Gson;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.helpers.MessageFormatter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A class for printing fancy log messages into the console.
 *
 * @author Moritz Jung
 */
public class Logger {
    /**
     * Whether debug logs should be active.
     */
    private static boolean debug = false;

    /**
     * Whether trace logs should be active.
     */
    private static boolean trace = false;

    /**
     * Debug and trace log blacklists;
     */
    private static String[] debugBlacklist = new String[0];

    /**
     * Folder Path for the log files.
     */
    private static String logFilePath = "";
    /**
     * The class to log for.
     */
    private final String name;
    /**
     * Describes the length of the loglevel with the most characters. Used for
     * aligning the timestamps.
     */
    private final int longestLogLevelString;
    /**
     * Instance of gson.
     */
    private final Gson gson;

    /**
     * The constructor for Logger.
     *
     * @param obj the obj
     */
    public Logger(Class<?> obj) {
        this(obj.getName());
    }

    /**
     * The constructor for Logger.
     *
     * @param name the name
     */
    public Logger(String name) {
        // Calculate the longest (in terms of characters) LogLevel
        Level longestLogLevel = Level.INFO;
        for (Level logLevel : Level.values()) {
            if (logLevel.toString().length() > longestLogLevel.toString().length()) {
                longestLogLevel = logLevel;
            }
        }
        longestLogLevelString = longestLogLevel.toString().length();
        this.name = name;
        this.gson = new Gson();
    }

    /**
     * Returns a logger object.
     *
     * @param obj the obj
     * @return Logger logger
     */
    public static Logger getLogger(Class<?> obj) {
        return new Logger(obj);
    }

    /**
     * Returns a logger object.
     *
     * @param name the name
     * @return Logger logger
     */
    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    /**
     * Whether to enable debug logs. Default: false
     *
     * @param enableDebug the enable debug
     */
    public static void enableDebug(boolean enableDebug) {
        debug = enableDebug;
        System.out.println("[LOGGER] Enabled debug logging.");
    }

    /**
     * Getter for debug.
     *
     * @return debug debug
     */
    public static boolean getDebug() {
        return debug;
    }

    /**
     * Whether to enable trace logs. Default: false
     *
     * @param enableTrace to enable trace
     */
    public static void enableTrace(boolean enableTrace) {
        trace = enableTrace;
        System.out.println("[LOGGER] Enabled debug logging.");
    }

    /**
     * Getter for trace.
     *
     * @return trace trace
     */
    public static boolean getTrace() {
        return trace;
    }

    /**
     * Set a blacklist for certain classes to not log debug level logs from them.
     *
     * @param blacklist String[] of snippets that occur in the class name
     */
    public static void setDebugBlacklist(String[] blacklist) {
        debugBlacklist = blacklist;
        System.out.println(MessageFormatter.format("[LOGGER] Debug blacklist set to: {}", blacklist).getMessage());
    }

    /**
     * Set the filepath for the logfiles.
     *
     * @param filePath the file path
     */
    public static void setLogFilePath(String filePath) {
        logFilePath = filePath;
        System.out.println(MessageFormatter.format("[LOGGER] Log file path set to: {}", filePath).getMessage());
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Logs an info.
     *
     * @param message the message
     */
    public void logInfo(String message) {
        log(Level.INFO, message);
    }

    /**
     * Logs a warning.
     *
     * @param message the message
     */
    public void logWarning(String message) {
        log(Level.WARNING, message);
    }

    /**
     * Logs an error message.
     *
     * @param message the message
     */
    public void logError(String message) {
        log(Level.ERROR, message);
    }

    /**
     * Logs an error.
     *
     * @param e the e
     */
    public void logError(Exception e) {
        if (Objects.equals(e, null)) {
            log(Level.ERROR, "Some Error occurred.");
        } else {
            log(Level.ERROR, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs an error.
     *
     * @param t the t
     */
    public void logError(Throwable t) {
        if (Objects.equals(t, null)) {
            log(Level.ERROR, "Some Error occurred.");
        } else {
            log(Level.ERROR, t.getMessage());
            t.printStackTrace();
        }
    }

    /**
     * Logs debug messages. Disabled if debug == false.
     *
     * @param message the message
     */
    public void logDebug(String message) {
        if (debug) {
            boolean filterMessage = false;
            for (int i = 0; i < debugBlacklist.length; i++) {
                if (name.contains(debugBlacklist[i])) {
                    filterMessage = true;
                    break;
                }
            }

            if (!filterMessage) {
                log(Level.DEBUG, message);
            }
        }
    }

    /**
     * Logs trace messages. Disabled if trace == false.
     *
     * @param message the message
     */
    public void logTrace(String message) {
        if (trace) {
            boolean filterMessage = false;
            for (int i = 0; i < debugBlacklist.length; i++) {
                if (name.contains(debugBlacklist[i])) {
                    filterMessage = true;
                    break;
                }
            }

            if (!filterMessage) {
                log(Level.TRACE, message);
            }
        }
    }

    /**
     * Logs a discord command.
     *
     * @param commandObject the command object
     * @param msg           the msg
     */
    public void logCommand(CommandObject commandObject, Message msg) {
        StringBuilder sb = new StringBuilder();

        sb.append(gson.toJson(commandObject));
        sb.append(" from ");
        sb.append(msg.getAuthor().getAsMention());
        sb.append(". original message: \"");
        sb.append(msg.getContentRaw());
        sb.append("\"");

        log(Level.COMMAND, sb.toString());
    }

    /**
     * Logs a discord command.
     *
     * @param commandObject the command object
     * @param message       the message
     */
    public void logCommand(CommandObject commandObject, String message) {
        StringBuilder sb = new StringBuilder();

        sb.append(commandObject.id);
        sb.append(" ");
        sb.append(message);

        log(Level.COMMAND, sb.toString());
    }

    /**
     * Logs a discord command.
     *
     * @param commandObject the command object
     * @param success       the success
     * @param message       the message
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
     * @param commandObject the command object
     * @param success       the success
     * @param e             the e
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
     * @param logLevel the log level
     * @param message  the message
     */
    public void log(Level logLevel, String message) {
        log(logLevel, message, true);
    }

    /**
     * Print a fancy log message in the console
     *
     * @param logLevel    the log level
     * @param message     the message
     * @param writeToFile whether to write to file
     */
    public void log(Level logLevel, String message, boolean writeToFile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(constructHeader(logLevel));
        stringBuilder.append(" ");
        stringBuilder.append(message);

        System.out.print(getColorCode(logLevel));
        System.out.print(stringBuilder);
        System.out.println(ConsoleColors.RESET);
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
     * @param logLevel the log level
     * @return String
     */
    private String constructHeader(Level logLevel) {
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
        stringBuilder.append(name);
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    /**
     * Calculate spacing after the log header based on the longest LogLevel
     *
     * @param logLevel the log level
     * @return String
     */
    private String calculateSpacing(Level logLevel) {
        return " ".repeat(Math.max(0, longestLogLevelString - logLevel.toString().length()));
    }

    /**
     * Gets an ansi color code based on the logLevel.
     *
     * @param logLevel the log level
     * @return ansi color code
     */
    private String getColorCode(Level logLevel) {
        if (Objects.equals(logLevel, Level.ERROR)) {
            return ConsoleColors.RED;
        } else if (Objects.equals(logLevel, Level.WARNING)) {
            return ConsoleColors.YELLOW;
        } else if (Objects.equals(logLevel, Level.INFO)) {
            return ConsoleColors.WHITE_BRIGHT;
        }
        return "";
    }

    /**
     * Writes a log to the log file.
     *
     * @param line the line to write to the file
     * @throws IOException on error while creating directories
     */
    private void logToFile(String line) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDateTime localDateTime = LocalDateTime.now();

        if (Objects.equals(logFilePath, "")) {
            log(Level.WARNING, "logFilePath is not set!", false);
            return;
        }

        File file = new File(logFilePath, "/logger/" + dateTimeFormatter.format(localDateTime) + "-LemonTTB-log.txt");
        File dir = file.getParentFile();

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Could not create parent directories");
            }
        } else if (!dir.isDirectory()) {
            throw new IOException("The parent file is not a directory");
        }

        try (Writer writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(line).append(System.lineSeparator());
        } catch (IOException e) {
            throw new IOException("Could not open the log file");
        }
    }

    /**
     * An enum describing the level of the log output.
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
         * Represents a trace level log. Disabled if trace == false.
         */
        TRACE,
        /**
         * Represents a log for a discord command.
         */
        COMMAND
    }
}
