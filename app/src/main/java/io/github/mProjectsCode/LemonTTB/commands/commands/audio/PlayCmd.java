/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021-2022
 * Developed by Moritz Jung
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

package io.github.mProjectsCode.LemonTTB.commands.commands.audio;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.IOHelper;
import io.github.mProjectsCode.LemonTTB.LemonTTB_Audio.AudioTrackSource;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * The type Play cmd.
 */
public class PlayCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Plays a song.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"play"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.AUDIO};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        boolean success = true;

        CommandObject.Argument argument = commandObject.getArgument("-s");
        if (!Objects.equals(argument, null)) {
            if (!Objects.equals(argument.value, null)) {
                File[] paths = IOHelper.findPathsByFileName(argument.value, App.audioPath);
                for (int i = 0; i < paths.length; i++) {
                    Command.LOGGER
                            .logTrace("Result " + i + " for search: " + argument.value + " is: " + paths[i].getPath());
                }
                if (paths.length > 0) {
                    Command.LOGGER.logCommand(commandObject,
                            "Result for search: " + argument.value + " is: " + paths[0].getPath());
                    App.audioManager.loadAndPlayTrack(paths[0].getPath(), AudioTrackSource.LOCAL);
                }
            }
        }

        argument = commandObject.getArgument("-y");
        if (!Objects.equals(argument, null)) {
            if (!Objects.equals(argument.value, null)) {
                App.audioManager.loadAndPlayTrack(argument.value, AudioTrackSource.YOUTUBE);
            }
        }

        argument = commandObject.getArgument("-l");
        App.audioManager.setLooping(!Objects.equals(argument, null));

        if (success) {
            Command.LOGGER.logCommand(commandObject, true, "");
            msg.reply("Successfully queued the song.").queue();
        } else {
            Command.LOGGER.logWarning("AudioPath for command " + commandObject.id + " is null");
            Command.LOGGER.logCommand(commandObject, success, "No file found");
            msg.reply("Failed to queue the song.").queue();
        }
    }

}
