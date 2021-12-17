package LemonTTB.commands.commands;

import java.util.Objects;

import LemonTTB.App;
import LemonTTB.Config;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class VolumeCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {

        CommandObject.Argument argument = commandObject.getArgument("-v");
        if (!Objects.equals(argument, null)) {
            if (!Objects.equals(argument.value, null)) {
                try {
                    int volume = Integer.parseInt(argument.value);
                    App.audioManager.setVolume(volume);
                    Command.LOGGER.logCommand(commandObject, true, "");
                    msg.reply("Set volume to " + volume).queue();

                    if (!Objects.equals(commandObject.getArgument("-s"), null)) {
                        Config.options.defaultVolume = volume;
                        Config.save();
                    }

                } catch (NumberFormatException e) {
                    Command.LOGGER.logCommand(commandObject, false, "Could not parse argument -v to an integer.");
                    return;
                }
            }
        } else {
            msg.reply("Volume is currently set to " + App.audioManager.getVolume()).queue();
        }

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
