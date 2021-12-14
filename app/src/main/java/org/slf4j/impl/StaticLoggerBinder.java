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

package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import LemonTTB.Logger.LoggerFactory;

public final class StaticLoggerBinder implements LoggerFactoryBinder {
    /**
     * Declare the version of the SLF4J API this implementation is compiled
     * against. The value of this field is usually modified with each release.
     */
    public static String REQUESTED_API_VERSION = "1.6"; // !final

    /**
     * The class string of the Logger Factory.
     */
    private static final String LOGGER_FACTORY_CLASS_STR = LoggerFactory.class.getName();

    /**
     * Holds the only instance of this class.
     */
    private static final StaticLoggerBinder INSTACE = new StaticLoggerBinder();

    /**
     * The ILoggerFactory instance returned by the {@link #getLoggerFactory}.
     */
    private final ILoggerFactory loggerFactory;

    /**
     * Private constructor to prevent multiple instances.
     */
    private StaticLoggerBinder() {
        loggerFactory = new LoggerFactory();
    }

    /**
     * Returns the instance of this class.
     * 
     * @return the instance
     */
    public static StaticLoggerBinder getSingleton() {
        return INSTACE;
    }

    /**
     * Returns the factory.
     * 
     * @return the factory
     */
    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    /**
     * Returns the class name.
     * 
     * @return the class name
     */
    @Override
    public String getLoggerFactoryClassStr() {
        return LOGGER_FACTORY_CLASS_STR;
    }

}
