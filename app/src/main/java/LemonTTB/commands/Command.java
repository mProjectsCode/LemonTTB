package LemonTTB.commands;

import LemonTTB.Logger;
import net.dv8tion.jda.api.entities.Message;

public abstract class Command {
    protected static final Logger LOGGER = Logger.getLogger(Command.class);

    public abstract void run(CommandObject commandObject, Message msg);
}
