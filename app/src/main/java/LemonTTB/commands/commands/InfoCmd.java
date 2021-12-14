/*******************************************************************************
 * 
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
 * 
 ******************************************************************************/

package LemonTTB.commands.commands;

import java.awt.Color;

import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class InfoCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(255, 255, 255));
        embed.setTitle("Lemon Table Top Bot (LemonTTB)", null);
        embed.setDescription(
                "The bot code was created by Moritz Jung (Lemons#5466).\n"
                        + "It is written in Java with JDA and licensed with GPL-3.0.\n"
                        + "The source code can be found on my github:\n"
                        + "https://github.com/mProjectsCode/LemonTTB.");

        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        super.LOGGER.logCommand(commandObject, true, "");
    }

}
