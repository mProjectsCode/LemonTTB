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

package io.github.mProjectsCode.LemonTTB.commands.commands.nameMappings;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Delete name mapping cmd.
 */
public class DeleteNameMappingCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Deletes a name mapping.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-u",
                                "String",
                                true,
                                "The username for who you want to delete the mapping."
                        )
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"deleteNameMapping"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.MODERATION};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        CommandObject.Argument userArgument = commandObject.getArgument("-u");

        if (Objects.equals(userArgument, null)) {
            msg.reply("Please specify the username of the person with \"-u\".").queue();
            Command.LOGGER.logCommand(commandObject, false, "No Username was specified.");
            return;
        }
        if (Objects.equals(userArgument.value, null) || Objects.equals(userArgument.value, "")) {
            msg.reply("The username can not be empty.").queue();
            Command.LOGGER.logCommand(commandObject, false, "Username was empty.");
            return;
        }

        String removedMapping = App.nameMappingsHandler.deleteNameMapping(userArgument.value);

        if (Objects.equals(removedMapping, null)) {
            msg.reply("There is no mapping for " + userArgument.value + ".").queue();
            return;
        } else {
            msg.reply("Removed mapping \"" + userArgument.value + " --> " + removedMapping + "\".").queue();
        }
        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
