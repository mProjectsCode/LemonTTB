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

package io.github.mProjectsCode.LemonTTB.commands.commands.nameMappings;

import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.commands.CommandDescription;
import io.github.mProjectsCode.LemonTTB.commands.CommandObject;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Map;

/**
 * The type Show name mappings cmd.
 */
public class ShowNameMappingsCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Shows a list of all the name mappings.",
                new CommandDescription.ArgumentDescription[]{

                });
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"showNameMappings"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[]{Permission.MODERATION};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : Config.options.nameMappings.entrySet()) {
            sb.append(entry.getKey()).append(" --> ").append(entry.getValue()).append("\n");
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(255, 255, 255));
        embed.setTitle("LemonTTB name mappings", null);
        embed.setDescription(sb.isEmpty() ?
                "There are no name mappings. You can create them with \"createNameMapping\"." :
                sb.toString()
        );

        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
