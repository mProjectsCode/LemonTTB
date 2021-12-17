package LemonTTB.commands.commands;

import java.awt.Color;

import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

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
    public void run(CommandObject commandObject, Message msg) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(255, 255, 255));
        embed.setTitle("Lemon Table Top Bot (LemonTTB)", null);
        embed.setDescription(
                "This bot was created by **Moritz Jung** (Lemons#5466).\n"
                        + "It is written in **Java** with **JDA** and licensed with **GPL-3.0**.\n"
                        + "The source code can be found on my **github**:\n"
                        + "https://github.com/mProjectsCode/LemonTTB.");

        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        Command.LOGGER.logCommand(commandObject, true, "");

    }

}
