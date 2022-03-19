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

import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

/**
 * The type Command.
 */
public abstract class Command {
    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = Logger.getLogger(Command.class);

    /**
     * Gets command description.
     *
     * @return the command description
     */
    @NotNull
    public abstract CommandDescription getCommandDescription();

    /**
     * Get command identifiers string [ ].
     *
     * @return the string [ ]
     */
    @NotNull
    public abstract String[] getCommandIdentifiers();

    /**
     * Get command permissions permission [ ].
     *
     * @return the permission [ ]
     */
    @NotNull
    public abstract Permission[] getCommandPermissions();

    /**
     * Run.
     *
     * @param commandObject the command object
     * @param msg           the msg
     */
    public abstract void run(CommandObject commandObject, Message msg);
}
