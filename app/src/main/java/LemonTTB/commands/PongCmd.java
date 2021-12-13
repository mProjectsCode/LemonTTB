package LemonTTB.commands;

import LemonTTB.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class PongCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {
        msg.getChannel().sendMessage("Pong!").queue();

        super.LOGGER.logCommand(commandObject, true, "");
    }

}
