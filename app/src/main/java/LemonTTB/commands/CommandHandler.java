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

package LemonTTB.commands;

import java.util.Objects;

import LemonTTB.Config;
import LemonTTB.Logger.Logger;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * The type Command handler.
 */
public class CommandHandler extends ListenerAdapter {
    private static final Logger LOGGER = Logger.getLogger(CommandHandler.class);

    private final CommandRegistry commandRegistry = new CommandRegistry();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
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

        selectCommand(commandObject, msg);
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

    private void selectCommand(CommandObject commandObject, Message msg) {
        Command command = commandRegistry.commands.get(commandObject.command);
        if (command == null) {
            return;
        }
        command.run(commandObject, msg);
    }
}
