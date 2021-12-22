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
import LemonTTB.commands.commands.permissions.AddPermissionCmd;
import LemonTTB.commands.commands.permissions.ResetPermissionsCmd;
import LemonTTB.commands.commands.permissions.ShowPermissionsCmd;

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
        // General
        registerCommand(new InfoCmd());
        registerCommand(new HelpCmd());
        registerCommand(new PingCmd());
        // Moderator
        registerCommand(new MoveCmd());
        // Vc
        registerCommand(new JoinCmd());
        registerCommand(new LeaveCmd());
        // Audio
        registerCommand(new PlayCmd());
        registerCommand(new PauseCmd());
        registerCommand(new SkipCmd());
        registerCommand(new LoopCmd());
        registerCommand(new VolumeCmd());
        registerCommand(new ClearQueueCmd());
        registerCommand(new ShowQueueCmd());
        // Name Mappings
        registerCommand(new CreateNameMappingCmd());
        registerCommand(new DeleteNameMappingCmd());
        registerCommand(new ShowNameMappingsCmd());
        // Permissions
        registerCommand(new AddPermissionCmd());
        registerCommand(new ResetPermissionsCmd());
        registerCommand(new ShowPermissionsCmd());
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