package LemonTTB;

import java.util.Objects;

import LemonTTB.commands.PongCmd;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    private static final Logger LOGGER = Logger.getLogger(CommandHandler.class);

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (Objects.equals(event.getChannel().getType(), ChannelType.PRIVATE)) {
            return;
        }

        Message msg = event.getMessage();

        CommandObject commandObject = parseCommand(msg);

        if (commandObject == null) {
            return;
        }

        LOGGER.logCommand(commandObject, msg);

        if (Objects.equals(commandObject.command, "ping")) {
            new PongCmd().run(commandObject, msg);
        }
    }

    private CommandObject parseCommand(Message msg) {
        if (!msg.getContentRaw().startsWith(Config.options.prefix)) {
            return null;
        }

        String[] msgParts = msg.getContentRaw().split(" ");

        if (msgParts.length == 0) {
            return null;
        }

        CommandObject commandObject = new CommandObject();
        commandObject.command = msgParts[0].replace(Config.options.prefix, "");
        commandObject.arguments = new CommandObject.Argument[msgParts.length - 1];

        for (int i = 1; i < msgParts.length; i++) {
            if (msgParts[i].startsWith("-")) {
                commandObject.arguments[i - 1] = new CommandObject.Argument();
                commandObject.arguments[i - 1].name = msgParts[i];
                if (!msgParts[i + 1].startsWith("-")) {
                    commandObject.arguments[i - 1].value = msgParts[i + 1];
                }
            }
        }

        return commandObject;
    }

    private void selectCommand(CommandObject commandObject) {
        if (Objects.equals(commandObject.command, "ping")) {

        }
    }
}
