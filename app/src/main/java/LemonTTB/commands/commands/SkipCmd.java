package LemonTTB.commands.commands;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class SkipCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {
        App.audioManager.skipTrack();

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
