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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import LemonTTB.commands.commands.InfoCmd;
import LemonTTB.commands.commands.JoinCmd;
import LemonTTB.commands.commands.PlayCmd;
import LemonTTB.commands.commands.PongCmd;

/**
 * CommandRegistery
 */
public class CommandRegistery {
    public Map<String, Command> commands;

    public CommandRegistery() {
        commands = new HashMap<String, Command>();
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(new InfoCmd(), new String[] { "info", "about" });
        registerCommand(new PongCmd(), "ping");
        registerCommand(new JoinCmd(), "join");
        registerCommand(new PlayCmd(), "play");
    }

    public void registerCommand(Command command, String[] keywords) {
        for (int i = 0; i < keywords.length; i++) {
            registerCommand(command, keywords[i]);
        }
    }

    public void registerCommand(Command command, String keyword) {
        commands.put(keyword, command);
    }
}