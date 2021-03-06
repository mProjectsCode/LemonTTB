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

package io.github.mProjectsCode.LemonTTB.commands.commands;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * The type Join cmd.
 */
public class JoinCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Joins the voice channel the message author is in.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"join"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.AUDIO};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        List<Guild> guilds = App.jda.getGuilds();

        for (int i = 0; i < guilds.size(); i++) {
            // LOGGER.logDebug(guilds.get(i).getName());
            List<GuildChannel> channels = guilds.get(i).getChannels();
            guilds.get(i).loadMembers();

            for (int j = 0; j < channels.size(); j++) {

                if (Objects.equals(channels.get(j).getType(), ChannelType.VOICE)) {
                    // LOGGER.logDebug(channels.get(j).getName());
                    List<Member> members = channels.get(j).getMembers();
                    // LOGGER.logDebug(Integer.toString(members.size()));

                    for (int k = 0; k < members.size(); k++) {
                        // LOGGER.logDebug(members.get(k).getUser().getName());

                        if (Objects.equals(members.get(k).getId(), msg.getAuthor().getId())) {
                            App.audioManager.connect(channels.get(j), guilds.get(i));
                            Command.LOGGER.logCommand(commandObject,
                                    "Successfully connected to " + channels.get(j).getName());
                        }
                    }
                }
            }
        }

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
