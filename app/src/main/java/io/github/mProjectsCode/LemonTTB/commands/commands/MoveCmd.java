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

package io.github.mProjectsCode.LemonTTB.commands.commands;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * The type Move cmd.
 */
public class MoveCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Moves a people fom one channel to another.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-b",
                                "boolean",
                                false,
                                "Whether to move them back to the primary channel."),
                        new CommandDescription.ArgumentDescription(
                                "-a",
                                "",
                                false,
                                "Whether to move everyone. If -u is also present, -u will act as a blacklist."),
                        new CommandDescription.ArgumentDescription(
                                "-u",
                                "string",
                                false,
                                "Specifies a specific user to be moved."),
                        new CommandDescription.ArgumentDescription(
                                "-me",
                                "boolean",
                                false,
                                "Whether to also move the message sender.")
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"move"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.MODERATION};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        String primaryChannelId = Config.options.primaryVoiceChannel;
        String secondaryChannelId = Config.options.secondaryVoiceChannel;

        VoiceChannel primaryChannel = null;
        VoiceChannel secondaryChannel = null;

        primaryChannel = (VoiceChannel) App.jda.getGuildChannelById(primaryChannelId);
        secondaryChannel = (VoiceChannel) App.jda.getGuildChannelById(secondaryChannelId);

        if (Objects.equals(primaryChannel, null)) {
            Command.LOGGER.logWarning("Primary channel not found.");
            return;
        }
        if (Objects.equals(secondaryChannel, null)) {
            Command.LOGGER.logWarning("Secondary channel not found.");
            return;
        }

        VoiceChannel moveToChannel = secondaryChannel;
        VoiceChannel moveFromChannel = primaryChannel;

        CommandObject.Argument userArgument = commandObject.getArgument("-u");
        CommandObject.Argument allArgument = commandObject.getArgument("-a");
        CommandObject.Argument backArgument = commandObject.getArgument("-b");
        CommandObject.Argument meArgument = commandObject.getArgument("-me");

        if (!Objects.equals(backArgument, null)) {
            moveToChannel = Boolean.parseBoolean(backArgument.value) ? secondaryChannel : primaryChannel;
            moveFromChannel = Boolean.parseBoolean(backArgument.value) ? primaryChannel : secondaryChannel;
        }
        Command.LOGGER.logTrace("Move to channel: " + moveToChannel.getName());
        Command.LOGGER.logTrace("Move from channel: " + moveFromChannel.getName());

        boolean allArgumentActive = !Objects.equals(allArgument, null);
        boolean userArgumentActive = !Objects.equals(userArgument, null) && !Objects.equals(userArgument.value, null);
        boolean meArgumentActive = false;
        if (!Objects.equals(meArgument, null)) {
            if (!Objects.equals(meArgument.value, null)) {
                meArgumentActive = Boolean.parseBoolean(meArgument.value);
            }
        }

        if (allArgumentActive) {
            List<Member> members = moveFromChannel.getMembers();
            for (int i = 0; i < members.size(); i++) {
                // if the user is the bot don't move it
                if (Objects.equals(members.get(i).getId(), App.jda.getSelfUser().getId())) {
                    continue;
                }
                // if we exclude the message author
                if (meArgumentActive) {
                    if (Objects.equals(members.get(i).getId(), msg.getAuthor().getId())) {
                        continue;
                    }
                }
                // if we exclude a specific user
                if (userArgumentActive) {
                    // if the user is the excluded one don't move it
                    if (Objects.equals(members.get(i).getUser().getName(), Config.options.nameMappings.get(userArgument.value))) {
                        continue;
                    }
                }
                moveFromChannel.getGuild().moveVoiceMember(members.get(i), moveToChannel).queue();
            }
        } else if (userArgumentActive) {
            List<Member> members = moveFromChannel.getMembers();
            for (int i = 0; i < members.size(); i++) {
                // if the username matches the argument move him
                if (Objects.equals(members.get(i).getUser().getName(), Config.options.nameMappings.get(userArgument.value))) {
                    moveFromChannel.getGuild().moveVoiceMember(members.get(i), moveToChannel).queue();
                }
                // if we also move the message author
                if (meArgumentActive) {
                    if (Objects.equals(members.get(i).getId(), msg.getAuthor().getId())) {
                        moveFromChannel.getGuild().moveVoiceMember(members.get(i), moveToChannel).queue();
                    }
                }
            }
        }

    }

}
