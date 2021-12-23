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

import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

/**
 * The type Logger adapter.
 */
public class LoggerAdapter implements org.slf4j.Logger {

    private final Logger logger;

    /**
     * Instantiates a new Logger adapter.
     *
     * @param logger the logger
     */
    public LoggerAdapter(Logger logger) {
        this.logger = logger;
    }

    /**
     * Instantiates a new Logger adapter.
     *
     * @param name the name
     */
    public LoggerAdapter(String name) {
        this.logger = new Logger(name);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public void trace(String msg) {
        logger.logTrace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        logger.logTrace(MessageFormatter.format(format, arg).getMessage());
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        logger.logTrace(MessageFormatter.format(format, arg1, arg2).getMessage());
    }

    @Override
    public void trace(String format, Object... arguments) {
        logger.logTrace(MessageFormatter.arrayFormat(format, arguments).getMessage());
    }

    @Override
    public void trace(String msg, Throwable t) {
        logger.logTrace(msg);
        logger.logError(t);
    }

    @Override
    public void trace(Marker marker, String msg) {
        trace(msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        trace(format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        trace(format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        trace(format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        trace(msg, t);
    }

    @Override
    public void debug(String msg) {
        logger.logDebug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        logger.logDebug(MessageFormatter.format(format, arg).getMessage());
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        logger.logDebug(MessageFormatter.format(format, arg1, arg2).getMessage());
    }

    @Override
    public void debug(String format, Object... arguments) {
        logger.logDebug(MessageFormatter.arrayFormat(format, arguments).getMessage());
    }

    @Override
    public void debug(String msg, Throwable t) {
        logger.logDebug(msg);
        logger.logError(t);
    }

    @Override
    public void debug(Marker marker, String msg) {
        debug(msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        debug(format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        debug(format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... argArray) {
        debug(format, argArray);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        debug(msg, t);
    }

    @Override
    public void error(String msg) {
        logger.logError(msg);
    }

    @Override
    public void error(String format, Object arg) {
        logger.logError(MessageFormatter.format(format, arg).getMessage());
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        logger.logError(MessageFormatter.format(format, arg1, arg2).getMessage());
    }

    @Override
    public void error(String format, Object... arguments) {
        logger.logError(MessageFormatter.arrayFormat(format, arguments).getMessage());
    }

    @Override
    public void error(String msg, Throwable t) {
        logger.logError(msg);
        logger.logError(t);
    }

    @Override
    public void error(Marker marker, String msg) {
        error(msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        error(format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        error(format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... argArray) {
        error(format, argArray);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        error(msg, t);
    }

    @Override
    public void warn(String msg) {
        logger.logWarning(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        logger.logWarning(MessageFormatter.format(format, arg).getMessage());
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        logger.logWarning(MessageFormatter.format(format, arg1, arg2).getMessage());
    }

    @Override
    public void warn(String format, Object... arguments) {
        logger.logWarning(MessageFormatter.arrayFormat(format, arguments).getMessage());
    }

    @Override
    public void warn(String msg, Throwable t) {
        logger.logWarning(msg);
        logger.logError(t);
    }

    @Override
    public void warn(Marker marker, String msg) {
        warn(msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        warn(format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        warn(format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... argArray) {
        warn(format, argArray);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        warn(msg, t);
    }

    @Override
    public void info(String msg) {
        logger.logInfo(msg);
    }

    @Override
    public void info(String format, Object arg) {
        logger.logInfo(MessageFormatter.format(format, arg).getMessage());
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        logger.logInfo(MessageFormatter.format(format, arg1, arg2).getMessage());
    }

    @Override
    public void info(String format, Object... arguments) {
        logger.logInfo(MessageFormatter.arrayFormat(format, arguments).getMessage());
    }

    @Override
    public void info(String msg, Throwable t) {
        logger.logInfo(msg);
        logger.logError(t);
    }

    @Override
    public void info(Marker marker, String msg) {
        info(msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        info(format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        info(format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... argArray) {
        info(format, argArray);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        info(msg, t);
    }

    @Override
    public boolean isTraceEnabled() {
        return Logger.getTrace();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return Logger.getTrace();
    }

    @Override
    public boolean isDebugEnabled() {
        return Logger.getDebug();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return Logger.getDebug();
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }
}
