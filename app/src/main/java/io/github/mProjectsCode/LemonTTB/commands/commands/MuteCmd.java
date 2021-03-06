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
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * The type Mute cmd.
 */
public class MuteCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Deaf-mutes people except the message author.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-a",
                                "",
                                false,
                                "Whether to deaf-mute everyone. If -u is also present, -u will act as a blacklist."),
                        new CommandDescription.ArgumentDescription(
                                "-u",
                                "string",
                                false,
                                "Specifies a specific user to be deaf-muted."),
                        new CommandDescription.ArgumentDescription(
                                "-b",
                                "boolean",
                                false,
                                "Whether to unmute them.")
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"mute"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.MODERATION};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        CommandObject.Argument userArgument = commandObject.getArgument("-u");
        CommandObject.Argument allArgument = commandObject.getArgument("-a");
        CommandObject.Argument unmuteArgument = commandObject.getArgument("-b");

        boolean allArgumentActive = !Objects.equals(allArgument, null);
        boolean userArgumentActive = !Objects.equals(userArgument, null) && !Objects.equals(userArgument.value, null);
        boolean unmuteArgumentActive = false;
        if (!Objects.equals(unmuteArgument, null)) {
            if (!Objects.equals(unmuteArgument.value, null)) {
                unmuteArgumentActive = Boolean.parseBoolean(unmuteArgument.value);
            } else {
                unmuteArgumentActive = true;
            }
        }

        VoiceChannel voiceChannel = null;

        List<Guild> guilds = App.jda.getGuilds();

        for (int i = 0; i < guilds.size(); i++) {
            List<GuildChannel> channels = guilds.get(i).getChannels();
            guilds.get(i).loadMembers();

            for (int j = 0; j < channels.size(); j++) {
                if (Objects.equals(channels.get(j).getType(), ChannelType.VOICE)) {
                    List<Member> members = channels.get(j).getMembers();

                    for (int k = 0; k < members.size(); k++) {
                        if (Objects.equals(members.get(k).getId(), msg.getAuthor().getId())) {
                            voiceChannel = (VoiceChannel) channels.get(j);
                            break;
                        }
                    }
                }
            }
        }

        if (Objects.equals(voiceChannel, null)) {
            return;
        }

        if (allArgumentActive) {
            List<Member> members = voiceChannel.getMembers();
            for (int i = 0; i < members.size(); i++) {
                // if the user is the bot don't deaf-mute it
                if (Objects.equals(members.get(i).getId(), App.jda.getSelfUser().getId())) {
                    continue;
                }
                // exclude the message author
                if (Objects.equals(members.get(i).getId(), msg.getAuthor().getId())) {
                    continue;
                }
                // if we exclude a specific user
                if (userArgumentActive) {
                    // if the user is the excluded one don't deaf-mute him
                    if (Objects.equals(members.get(i).getUser().getName(), Config.options.nameMappings.get(userArgument.value))) {
                        continue;
                    }
                }
                if (unmuteArgumentActive) {
                    voiceChannel.getGuild().deafen(members.get(i), false).queue();
                    voiceChannel.getGuild().mute(members.get(i), false).queue();
                } else {
                    voiceChannel.getGuild().deafen(members.get(i), true).queue();
                    voiceChannel.getGuild().mute(members.get(i), true).queue();
                }
            }
        } else if (userArgumentActive) {
            List<Member> members = voiceChannel.getMembers();
            for (int i = 0; i < members.size(); i++) {
                // if the username matches the argument deaf-mute him
                if (Objects.equals(members.get(i).getUser().getName(), Config.options.nameMappings.get(userArgument.value))) {
                    if (unmuteArgumentActive) {
                        voiceChannel.getGuild().deafen(members.get(i), false).queue();
                        voiceChannel.getGuild().mute(members.get(i), false).queue();
                    } else {
                        voiceChannel.getGuild().deafen(members.get(i), true).queue();
                        voiceChannel.getGuild().mute(members.get(i), true).queue();
                    }
                }
            }
        }

    }
}
