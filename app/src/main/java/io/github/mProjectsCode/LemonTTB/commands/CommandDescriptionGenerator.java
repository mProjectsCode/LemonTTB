/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021-2022
 * Developed by Moritz Jung
 *
 * LemonTTB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LemonTTB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LemonTTB.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.github.mProjectsCode.LemonTTB.commands;

import io.github.mProjectsCode.LemonTTB.IOHelper;

import java.io.File;
import java.io.IOException;

/**
 * Generate a file containing all the compiled command descriptions for all registered commands.
 */
public class CommandDescriptionGenerator {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
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
