package LemonTTB.commands.commands;

import java.awt.Color;

import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
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
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"help"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[] {Permission.COMMAND};
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

        embed = CommandDescription.parseDescriptionAsEmbed(new HelpCmd(), "Help Command");
        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        embed = CommandDescription.parseDescriptionAsEmbed(new MoveCmd(), "Move Command");
        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        Command.LOGGER.logCommand(commandObject, true, "");

    }

}
