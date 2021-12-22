package LemonTTB.commands.commands.permissions;

import LemonTTB.App;
import LemonTTB.Config;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import LemonTTB.users.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class ShowPermissionsCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Shows all the permission a user has.",
                new CommandDescription.ArgumentDescription[] {

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"showPermissions"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[] {Permission.OWNER};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        CommandObject.Argument idArgument = commandObject.getArgument("-u");

        if (Objects.equals(idArgument, null)) {
            msg.reply("Please specify an id for the person with \"-u\".").queue();
            Command.LOGGER.logCommand(commandObject, false, "No id was specified.");
            return;
        }
        if (Objects.equals(idArgument.value, null) || Objects.equals(idArgument.value, "")) {
            msg.reply("The id can not be empty.").queue();
            Command.LOGGER.logCommand(commandObject, false, "Id was empty.");
            return;
        }

        User user = App.userHandler.getUserFromID(idArgument.value);
        StringBuilder sb = new StringBuilder();

        for (Permission permission : user.defaultPermissions) {
            sb.append(permission.name()).append("\n");
        }

        for (Permission permission : user.getPermissions()) {
            sb.append(permission.name()).append("\n");
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(255, 255, 255));
        embed.setTitle("LemonTTB " + App.jda.getUserById(user.getId()).getName() + " permissions", null);
        embed.setDescription(sb.toString());

        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
