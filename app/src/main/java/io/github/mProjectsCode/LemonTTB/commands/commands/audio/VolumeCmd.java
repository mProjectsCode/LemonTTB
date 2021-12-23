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

package io.github.mProjectsCode.LemonTTB.commands.commands.audio;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Volume cmd.
 */
public class VolumeCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Sets the volume of the audio player.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-v",
                                "integer",
                                false,
                                "The volume to set the bot to."),
                        new CommandDescription.ArgumentDescription(
                                "-s",
                                "boolean",
                                false,
                                "Whether to save the value as the default to the config.")
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"volume"};
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
                try {
                    int volume = Integer.parseInt(argument.value);
                    App.audioManager.setVolume(volume);
                    Command.LOGGER.logCommand(commandObject, true, "");
                    msg.reply("Set volume to " + volume).queue();

                    if (!Objects.equals(commandObject.getArgument("-s"), null)) {
                        Config.options.defaultVolume = volume;
                        Config.save();
                    }

                } catch (NumberFormatException e) {
                    Command.LOGGER.logCommand(commandObject, false, "Could not parse argument -v to an integer.");
                    return;
                }
            }
        } else {
            msg.reply("Volume is currently set to " + App.audioManager.getVolume()).queue();
        }

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
