package LemonTTB.commands.commands;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

public class SkipCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Skips the song, the audio player is currently playing.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"skip"};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        App.audioManager.skipTrack();

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
