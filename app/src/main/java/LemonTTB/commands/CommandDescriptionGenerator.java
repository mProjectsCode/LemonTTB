package LemonTTB.commands;

import LemonTTB.App;
import LemonTTB.IOHelper;

import java.io.File;
import java.io.IOException;

/**
 * Generate a file containing all the compiled command descriptions for all registered commands.
 */
public class CommandDescriptionGenerator {
    public static void main(String[] args) {
        CommandRegistry commandRegistry = new CommandRegistry();
        StringBuilder sb = new StringBuilder();
        File documentationFile = new File("./app/data/documentation/CommandDocumentation.txt");

        sb.append("# This is the command documentation for LemonTTB.\n");
        sb.append("# This file is computer generated.\n");
        sb.append("#\n");
        sb.append("# You can find the source code for the commands here:\n");
        sb.append("# https://github.com/mProjectsCode/LemonTTB/tree/master/app/src/main/java/LemonTTB/commands\n");
        sb.append("#\n\n");

        commandRegistry.commands.forEach((s, command) -> {
            sb.append(CommandDescription.parseDescriptionAsString(command, s));
        });

        try {
            IOHelper.write(documentationFile, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
