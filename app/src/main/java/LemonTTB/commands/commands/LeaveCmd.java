package LemonTTB.commands.commands;

import java.util.Objects;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;

public class LeaveCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {
        GuildChannel channel = App.audioManager.getCannel();
        if (!Objects.equals(channel, null)) {
            App.audioManager.leave();
            Command.LOGGER.logCommand(commandObject, "Successfuly left " + channel.getName());
        }

        Command.LOGGER.logCommand(commandObject, true, "");
    }

}
