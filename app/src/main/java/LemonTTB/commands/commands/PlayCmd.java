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
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class PlayCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {
        // LOGGER.logDebug(Boolean.toString((new File(App.audioPath, "/Into the Mists
        // E.mp3")).exists()));

        // TODO: FIX THIS SHIT
        CommandObject.Argument argument = commandObject.getArgument("-s");
        if (!Objects.equals(argument, null)) {
            if (!Objects.equals(argument.value, null)) {
                String[] pathToTrack = argument.value.split("/");

                for (int i = 0; i < pathToTrack.length; i++) {
                    File folder = App.audioPath;
                    File[] listOfFiles = folder.listFiles();

                    for (int j = 0; j < listOfFiles.length; j++) {
                        if (listOfFiles[j].isFile()) {

                        } else if (listOfFiles[j].isDirectory()) {

                        }
                    }
                }
            }
        }

        App.audioManager.loadAndPlayTrack(new File(App.audioPath, "/Into the Mists E.mp3").getPath());

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
