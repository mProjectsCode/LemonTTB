package LemonTTB.commands.commands.audio;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

public class ClearQueueCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Resets the queue of the audio player and disables looping",
                new CommandDescription.ArgumentDescription[]{

        });
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"resetQueue", "clearQueue"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[] {Permission.AUDIO};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {

        App.audioManager.clearQueue();
        App.audioManager.setLooping(false);

        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
