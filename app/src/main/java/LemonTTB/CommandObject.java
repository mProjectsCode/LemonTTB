package LemonTTB;

import java.util.UUID;

public class CommandObject {
    public UUID id;
    public String command;
    public Argument[] arguments;

    public CommandObject() {
        id = UUID.randomUUID();
    }

    public CommandObject(String command, Argument[] arguments) {
        this();
        this.command = command;
        this.arguments = arguments;
    }

    public static class Argument {
        public String name;
        public String value;
    }
}
