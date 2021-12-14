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

package LemonTTB.commands;

import java.util.UUID;

public class CommandObject {
    public UUID id;
    public String command;
    public Argument[] arguments;

    public CommandObject() {
        id = UUID.randomUUID();
    }

    public CommandObject(String command, Argument[] arguments) {
        this();
        this.command = command;
        this.arguments = arguments;
    }

    public static class Argument {
        public String name;
        public String value;
    }
}
