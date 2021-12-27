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
 *
 */

package io.github.mProjectsCode.LemonTTB.exceptions;

import io.github.mProjectsCode.LemonTTB.App;

/**
 * The type Start up exception.
 */
public class StartUpException extends Exception {
    private final App.StartUpPhase source;

    /**
     * Instantiates a new Start up exception.
     *
     * @param message the message
     * @param source  the source
     */
    public StartUpException(String message, App.StartUpPhase source) {
        super(message);
        this.source = source;
    }

    /**
     * Instantiates a new Start up exception.
     *
     * @param message the message
     * @param cause   the cause
     * @param source  the source
     */
    public StartUpException(String message, Throwable cause, App.StartUpPhase source) {
        super(message, cause);
        this.source = source;
    }

    /**
     * Instantiates a new Start up exception.
     *
     * @param cause  the cause
     * @param source the source
     */
    public StartUpException(Throwable cause, App.StartUpPhase source) {
        super(cause);
        this.source = source;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public App.StartUpPhase getSource() {
        return source;
    }
}
