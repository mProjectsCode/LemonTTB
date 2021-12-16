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

package LemonTTB.commands.commands;

import java.io.File;
import java.util.Objects;

import LemonTTB.App;
import LemonTTB.IOHelper;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class PlayCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {
        boolean sucess = true;

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
                    App.audioManager.loadAndPlayTrack(paths[0].getPath());
                }
            }
        }

        argument = commandObject.getArgument("-l");
        App.audioManager.setLooping(!Objects.equals(argument, null));

        if (sucess) {
            Command.LOGGER.logCommand(commandObject, sucess, "");
        } else {
            Command.LOGGER.logWarning("AudioPath for command " + commandObject.id + " is null");
            Command.LOGGER.logCommand(commandObject, sucess, "No file found");
        }
    }

}
