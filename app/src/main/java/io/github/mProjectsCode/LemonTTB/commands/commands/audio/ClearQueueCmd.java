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

/**
 * The type Clear queue cmd.
 */
public class ClearQueueCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Resets the queue of the audio player and disables looping",
                new CommandDescription.ArgumentDescription[]{

                });
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"resetQueue", "clearQueue"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.AUDIO};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {

        App.audioManager.clearQueue();
        App.audioManager.setLooping(false);

        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
