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

package io.github.mProjectsCode.LemonTTB.commands;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.users.User;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

/**
 * The type Command handler.
 */
public class CommandHandler extends ListenerAdapter {
    private static final Logger LOGGER = Logger.getLogger(CommandHandler.class);

    private final CommandRegistry commandRegistry = new CommandRegistry();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (Objects.equals(event.getAuthor().getId(), App.jda.getSelfUser().getId())) {
            return;
        }

        if (Objects.equals(event.getChannel().getType(), ChannelType.PRIVATE)) {
            return;
        }

        Message msg = event.getMessage();

        CommandObject commandObject = parseCommand(msg);

        if (commandObject == null) {
            return;
        }

        LOGGER.logCommand(commandObject, msg);

        executeCommand(commandObject, msg);
    }

    private CommandObject parseCommand(Message msg) {
        if (!msg.getContentRaw().startsWith(Config.options.prefix)) {
            return null;
        }

        String[] msgParts = msg.getContentRaw().split(" ");

        if (msgParts.length == 0) {
            return null;
        }

        CommandObject commandObject = new CommandObject();
        commandObject.command = msgParts[0].replace(Config.options.prefix, "");
        commandObject.arguments = new CommandObject.Argument[msgParts.length - 1];

        for (int i = 1; i < msgParts.length; i++) {
            if (msgParts[i].startsWith("-")) {
                commandObject.arguments[i - 1] = new CommandObject.Argument();
                commandObject.arguments[i - 1].identifier = msgParts[i];
                commandObject.arguments[i - 1].value = null;
                for (int j = i + 1; j < msgParts.length && !msgParts[j].startsWith("-"); j++) {
                    if (Objects.equals(commandObject.arguments[i - 1].value, null)) {
                        commandObject.arguments[i - 1].value = msgParts[j];
                        LOGGER.logTrace("init " + msgParts[j]);
                    } else {
                        commandObject.arguments[i - 1].value += " " + msgParts[j];
                        LOGGER.logTrace("added " + msgParts[j]);
                    }
                }
            }
        }

        return commandObject;
    }

    private void executeCommand(CommandObject commandObject, Message msg) {
        Command command = commandRegistry.commands.get(commandObject.command);
        if (command == null) {
            return;
        }

        String authorId = msg.getAuthor().getId();
        User user = App.userHandler.getUserFromID(authorId);

        if (user.hasPermissions(command.getCommandPermissions())) {
            command.run(commandObject, msg);
        } else {
            msg.reply("You do not have the permissions to run this command. If you think this is an error please contact " + App.jda.getUserById(Config.options.botOwner).getName() + ".").queue();
            LOGGER.logWarning(msg.getAuthor().getName() + " tried to execute the command \"" + command.getCommandIdentifiers()[0] + "\", which they do not have the permission to do.");
        }
    }
}
