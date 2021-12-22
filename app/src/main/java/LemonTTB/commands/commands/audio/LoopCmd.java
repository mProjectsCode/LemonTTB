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

package LemonTTB.commands.commands.audio;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Loop cmd.
 */
public class LoopCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Activates ot deactivates the looping functionality of the audio player. If no argument is present it will return whether looping is enabled.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-v",
                                "boolean",
                                false,
                                "Whether to set looping to ture or false.")
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"loop"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.AUDIO};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {

        CommandObject.Argument argument = commandObject.getArgument("-v");
        if (!Objects.equals(argument, null)) {
            if (!Objects.equals(argument.value, null)) {
                boolean looping = Boolean.parseBoolean(argument.value);
                App.audioManager.setLooping(looping);
                Command.LOGGER.logCommand(commandObject, true, "");
                msg.reply("Turned track looping" + (looping ? "on" : "off")).queue();
            }
        } else {
            msg.reply("Track looping is currently set to " + (App.audioManager.isLooping() ? "on" : "off")).queue();
        }

        Command.LOGGER.logCommand(commandObject, true, "");

    }

}
