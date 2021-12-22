package LemonTTB.commands.commands.audio;

import java.util.Objects;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

public class PauseCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Pauses, unpauses or returns the pause status of the audio player.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"pause", "unpause", "isPaused"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[] {Permission.AUDIO};
    }

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
