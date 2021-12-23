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

package io.github.mProjectsCode.LemonTTB.commands;

import java.util.Objects;
import java.util.UUID;

/**
 * The type Command object.
 */
public class CommandObject {
    /**
     * The ID.
     */
    public UUID id;
    /**
     * The Command.
     */
    public String command;
    /**
     * The Arguments.
     */
    public Argument[] arguments;

    /**
     * Instantiates a new Command object.
     */
    public CommandObject() {
        id = UUID.randomUUID();
    }

    /**
     * Instantiates a new Command object.
     *
     * @param command   the command
     * @param arguments the arguments
     */
    public CommandObject(String command, Argument[] arguments) {
        this();
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * Gets an argument based on the identifier.
     *
     * @param argumentIdentifier the argument identifier
     * @return the argument
     */
    public Argument getArgument(String argumentIdentifier) {
        for (int i = 0; i < arguments.length; i++) {
            if (!Objects.equals(arguments[i], null)) {
                if (Objects.equals(arguments[i].identifier, argumentIdentifier)) {
                    return arguments[i];
                }
            }
        }

        return null;
    }

    /**
     * The type Argument.
     */
    public static class Argument {
        /**
         * The Identifier.
         */
        public String identifier;
        /**
         * The Value.
         */
        public String value;
    }
}
