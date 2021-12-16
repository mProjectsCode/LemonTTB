package LemonTTB.commands.commands;

import java.util.Objects;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class PauseCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {

        if (Objects.equals(commandObject.command, "pause")) {
            App.audioManager.setPaused(true);
            msg.reply("Paused the audio player").queue();
        } else if (Objects.equals(commandObject.command, "unpause")) {
            App.audioManager.setPaused(false);
            msg.reply("Unpaused the audio player").queue();
        } else if (Objects.equals(commandObject.command, "ispaused")) {
            msg.reply("Audio player is currently " + (App.audioManager.isPaused() ? "paused" : "unpaused")).queue();
        }

        Command.LOGGER.logCommand(commandObject, true, "");

    }

}
