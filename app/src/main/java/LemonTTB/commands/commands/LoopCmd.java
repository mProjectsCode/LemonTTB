package LemonTTB.commands.commands;

import java.util.Objects;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

public class LoopCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Activates ot deactivates the looping functionality of the audio player. If no argument is present it will return whether looping is enabled.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-v",
                                "boolean",
                                false,
                                "Whether to set looping to ture or false.")
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"loop"};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {

        CommandObject.Argument argument = commandObject.getArgument("-v");
        if (!Objects.equals(argument, null)) {
            if (!Objects.equals(argument.value, null)) {
                boolean looping = Boolean.parseBoolean(argument.value);
                App.audioManager.setLooping(looping);
                Command.LOGGER.logCommand(commandObject, true, "");
                msg.reply("Turned track looping" + (looping ? "on" : "off")).queue();
            }
        } else {
            msg.reply("Track looping is currently set to " + (App.audioManager.isLooping() ? "on" : "off")).queue();
        }

        Command.LOGGER.logCommand(commandObject, true, "");

    }

}
