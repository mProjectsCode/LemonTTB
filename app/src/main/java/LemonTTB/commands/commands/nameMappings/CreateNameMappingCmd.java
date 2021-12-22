package LemonTTB.commands.commands.nameMappings;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CreateNameMappingCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Creates a new name mapping or overrides an existing one.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-u",
                                "String",
                                true,
                                "The username of the user. Not the nickname on the server."
                        ),
                        new CommandDescription.ArgumentDescription(
                                "-n",
                                "String",
                                true,
                                "The name you want to reference the user by."
                        )
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[]{"createNameMapping"};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        CommandObject.Argument nameArgument = commandObject.getArgument("-n");
        CommandObject.Argument userArgument = commandObject.getArgument("-u");

        if (Objects.equals(nameArgument, null)) {
            msg.reply("Please specify a name for the person with \"-n\".").queue();
            Command.LOGGER.logCommand(commandObject, false, "No name was specified.");
            return;
        }
        if (Objects.equals(nameArgument.value, null) || Objects.equals(nameArgument.value, "")) {
            msg.reply("The name can not be empty.").queue();
            Command.LOGGER.logCommand(commandObject, false, "Name was empty.");
            return;
        }

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

        App.nameMappingsHandler.createNameMapping(userArgument.value, nameArgument.value);

        msg.reply("Created mapping \"" + userArgument.value + " --> " + nameArgument.value + "\".").queue();

        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
