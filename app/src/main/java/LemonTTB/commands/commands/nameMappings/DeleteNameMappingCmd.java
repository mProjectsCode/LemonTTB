package LemonTTB.commands.commands.nameMappings;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DeleteNameMappingCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Deletes a name mapping.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-u",
                                "String",
                                true,
                                "The username for who you want to delete the mapping."
                        )
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"deleteNameMapping"};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        CommandObject.Argument userArgument = commandObject.getArgument("-u");

        if (Objects.equals(userArgument, null)) {
            msg.reply("Please specify the username of the person with \"-u\".").queue();
            Command.LOGGER.logCommand(commandObject, false, "No Username was specified.");
            return;
        }
        if (Objects.equals(userArgument.value, null) || Objects.equals(userArgument.value, "")) {
            msg.reply("The username can not be empty.").queue();
            Command.LOGGER.logCommand(commandObject, false, "Username was empty.");
            return;
        }

        String removedMapping = App.nameMappingsHandler.deleteNameMapping(userArgument.value);

        if (Objects.equals(removedMapping, null)) {
            msg.reply("There is no mapping for " + userArgument.value + ".").queue();
            return;
        } else {
            msg.reply("Removed mapping \"" + userArgument.value + " --> " + removedMapping + "\".").queue();
        }
        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
