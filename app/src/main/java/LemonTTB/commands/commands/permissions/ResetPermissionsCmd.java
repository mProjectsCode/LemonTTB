package LemonTTB.commands.commands.permissions;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ResetPermissionsCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Removes all the permission a user has.",
                new CommandDescription.ArgumentDescription[] {

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"resetPermissions", "removePermissions"};
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

        App.permissionHandler.resetPermissions(idArgument.value);
    }
}
