package LemonTTB.commands.commands.nameMappings;

import LemonTTB.Config;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Map;

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
        return new String[] {"showNameMappings"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[] {Permission.MODERATION};
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
