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

import java.util.List;
import java.util.Objects;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

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
                                    "Successfuly connected to " + channels.get(j).getName());
                        }
                    }
                }
            }
        }

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
