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

import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * The type Help cmd.
 */
public class HelpCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Gives information about the bot and all its commands.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"help"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.COMMAND};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(255, 255, 255));
        embed.setTitle("Lemon Table Top Bot (LemonTTB)", null);
        embed.setDescription(
                """
                        This bot was created by **Moritz Jung** (Lemons#5466).
                        It is written in **Java** with **JDA** and licensed with **GPL-3.0**.
                        The source code can be found on my **github**:
                        https://github.com/mProjectsCode/LemonTTB.""");

        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        msg.getChannel().sendMessage("A list of command can be found here https://github.com/mProjectsCode/LemonTTB/blob/master/app/data/documentation/CommandDocumentation.txt").queue();

        Command.LOGGER.logCommand(commandObject, true, "");

    }

}
