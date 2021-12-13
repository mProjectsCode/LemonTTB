package LemonTTB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import LemonTTB.commands.Command;
import LemonTTB.commands.InfoCmd;
import LemonTTB.commands.JoinCmd;
import LemonTTB.commands.PlayCmd;
import LemonTTB.commands.PongCmd;

/**
 * CommandRegistery
 */
public class CommandRegistery {
    public Map<String, Command> commands;

    public CommandRegistery() {
        commands = new HashMap<String, Command>();
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(new InfoCmd(), new String[] { "info", "about" });
        registerCommand(new PongCmd(), "ping");
        registerCommand(new JoinCmd(), "join");
        registerCommand(new PlayCmd(), "play");
    }

    public void registerCommand(Command command, String[] keywords) {
        for (int i = 0; i < keywords.length; i++) {
            registerCommand(command, keywords[i]);
        }
    }

    public void registerCommand(Command command, String keyword) {
        commands.put(keyword, command);
    }
}