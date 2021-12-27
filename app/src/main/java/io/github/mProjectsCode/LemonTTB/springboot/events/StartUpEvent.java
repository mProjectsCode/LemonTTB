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

package io.github.mProjectsCode.LemonTTB.springboot.events;

/**
 * The type Start up event.
 */
@Deprecated
public class StartUpEvent {
    /**
     * The Component.
     */
    public String component;
    /**
     * The Result.
     */
    public Result result;

    /**
     * Instantiates a new Start up event.
     *
     * @param component the component
     * @param result    the result
     */
    public StartUpEvent(String component, Result result) {
        this.component = component;
        this.result = result;
    }

    /**
     * The enum Result.
     */
    public enum Result {
        /**
         * Error result.
         */
        ERROR,
        /**
         * Warning result.
         */
        WARNING,
        /**
         * Ok result.
         */
        OK
    }
}
