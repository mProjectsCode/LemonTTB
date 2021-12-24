/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021
 * Programmed by Moritz Jung
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
 */

package io.github.mProjectsCode.LemonTTB.permissions;

/**
 * The values represent levels.
 * A permission of a certain level has automatically all permissions with a lower level.
 */
public enum Permission {
    /**
     * Command permission.
     */
    COMMAND(1),
    /**
     * Audio permission.
     */
    AUDIO(2),
    /**
     * Moderation permission.
     */
    MODERATION(2),
    /**
     * Admin permission.
     */
    ADMIN(4),
    /**
     * Owner permission.
     */
    OWNER(5);

    /**
     * The Value.
     */
    public final int value;

    Permission(int value) {
        this.value = value;
    }

    /**
     * Parse permission permission.
     *
     * @param str the str
     * @return the permission
     */
    public static Permission parsePermission(String str) {
        return switch (str.toLowerCase()) {
            case "command" -> COMMAND;
            case "audio" -> AUDIO;
            case "moderation" -> MODERATION;
            case "admin" -> ADMIN;
            case "owner" -> OWNER;
            default -> null;
        };
    }
}
