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

package LemonTTB.commands.commands.permissions;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Add permission cmd.
 */
public class AddPermissionCmd extends Command {
    // TODO: write command description
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Adds a permission to an user.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"addPermission"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.OWNER};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        CommandObject.Argument idArgument = commandObject.getArgument("-u");
        CommandObject.Argument permissionArgument = commandObject.getArgument("-p");

        if (Objects.equals(idArgument, null)) {
            msg.reply("Please specify an id for the person with \"-u\".").queue();
            Command.LOGGER.logCommand(commandObject, false, "No id was specified.");
            return;
        }
        if (Objects.equals(idArgument.value, null) || Objects.equals(idArgument.value, "")) {
            msg.reply("The id can not be empty.").queue();
            Command.LOGGER.logCommand(commandObject, false, "Id was empty.");
            return;
        }

        if (Objects.equals(permissionArgument, null)) {
            msg.reply("Please specify the permission you want to grant the user with \"-u\".").queue();
            Command.LOGGER.logCommand(commandObject, false, "No permission was specified.");
            return;
        }
        if (Objects.equals(permissionArgument.value, null) || Objects.equals(permissionArgument.value, "")) {
            msg.reply("The permission can not be empty.").queue();
            Command.LOGGER.logCommand(commandObject, false, "Permission was empty.");
            return;
        }

        Permission permission = Permission.parsePermission(permissionArgument.value);
        if (Objects.equals(permission, null)) {
            return;
        }
        App.permissionHandler.addPermissions(idArgument.value, permission);
    }
}
