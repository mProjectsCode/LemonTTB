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
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Pause cmd.
 */
public class PauseCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Pauses, unpauses or returns the pause status of the audio player.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"pause", "unpause", "isPaused"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.AUDIO};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {

        if (Objects.equals(commandObject.command, "pause")) {
            App.audioManager.setPaused(true);
            msg.reply("Paused the audio player").queue();
        } else if (Objects.equals(commandObject.command, "unpause")) {
            App.audioManager.setPaused(false);
            msg.reply("Unpaused the audio player").queue();
        } else if (Objects.equals(commandObject.command, "ispaused")) {
            msg.reply("Audio player is currently " + (App.audioManager.isPaused() ? "paused" : "unpaused")).queue();
        }

        Command.LOGGER.logCommand(commandObject, true, "");

    }

}
