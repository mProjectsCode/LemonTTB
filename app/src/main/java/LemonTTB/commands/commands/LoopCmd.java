package LemonTTB.commands.commands;

import java.util.Objects;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class LoopCmd extends Command {

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
