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
import java.util.Map;

import LemonTTB.commands.commands.*;
import LemonTTB.commands.commands.audio.*;
import LemonTTB.commands.commands.nameMappings.*;

/**
 * CommandRegistry
 */
public class CommandRegistry {
    /**
     * The Commands.
     */
    public Map<String, Command> commands;

    /**
     * Instantiates a new Command registry.
     */
    public CommandRegistry() {
        commands = new HashMap<String, Command>();
        registerCommands();
    }

    /**
     * Register commands.
     */
    public void registerCommands() {
        registerCommand(new InfoCmd());
        registerCommand(new PingCmd());
        registerCommand(new JoinCmd());
        registerCommand(new PlayCmd());
        registerCommand(new SkipCmd());
        registerCommand(new HelpCmd());
        registerCommand(new VolumeCmd());
        registerCommand(new LoopCmd());
        registerCommand(new PauseCmd());
        registerCommand(new MoveCmd());
        registerCommand(new ClearQueueCmd());
        registerCommand(new ShowQueueCmd());
        registerCommand(new CreateNameMappingCmd());
        registerCommand(new DeleteNameMappingCmd());
        registerCommand(new ShowNameMappingsCmd());
    }

    /**
     * Register command.
     *
     * @param command the command
     */
    public void registerCommand(Command command) {
        for (int i = 0; i < command.getCommandIdentifiers().length; i++) {
            commands.put(command.getCommandIdentifiers()[i], command);
        }
    }
}