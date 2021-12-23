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

import com.google.gson.Gson;
import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.users.User;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Permission handler.
 */
public class PermissionHandler {
    /**
     * The constant LOGGER.
     */
    public final static Logger LOGGER = Logger.getLogger(PermissionHandler.class);

    /**
     * The Gson.
     */
    public Gson gson;

    /**
     * Instantiates a new Permission handler.
     *
     * @param giveOwnerPermission the give owner permission
     */
    public PermissionHandler(boolean giveOwnerPermission) {
        gson = new Gson();
        if (giveOwnerPermission) {
            // on startup make sure the bot owner always has the owner permission
            if (Objects.equals(Config.options.botOwner, null) || Objects.equals(Config.options.botOwner, "")) {
                LOGGER.logWarning("The bot owner is not set in the config. Please set it.");
            } else {
                User botOwner = App.userHandler.getUserFromID(Config.options.botOwner);
                resetPermissions(botOwner);
                addPermissions(botOwner, Permission.OWNER);
            }
        }
    }

    /**
     * Has permissions boolean.
     *
     * @param id          the id
     * @param permissions the permissions
     * @return the boolean
     */
    public boolean hasPermissions(@NotNull String id, Permission[] permissions) {
        return App.userHandler.getUserFromID(id).hasPermissions(permissions);
    }

    /**
     * Add permissions.
     *
     * @param user        the user
     * @param permissions the permissions
     */
    public void addPermissions(@NotNull User user, Permission[] permissions) {
        Permission[] userPermissions = user.getPermissions();
        Permission[] newUserPermissions = new Permission[userPermissions.length + permissions.length];

        System.arraycopy(userPermissions, 0, newUserPermissions, 0, userPermissions.length);
        System.arraycopy(permissions, 0, newUserPermissions, userPermissions.length, permissions.length);

        user.setPermissions(newUserPermissions);

        App.userHandler.saveUser(user);
    }

    /**
     * Add permissions.
     *
     * @param id          the id
     * @param permissions the permissions
     */
    public void addPermissions(@NotNull String id, Permission[] permissions) {
        User user = App.userHandler.getUserFromID(id);
        addPermissions(user, permissions);
    }

    /**
     * Add permissions.
     *
     * @param user       the user
     * @param permission the permission
     */
    public void addPermissions(@NotNull User user, Permission permission) {
        addPermissions(user, new Permission[]{permission});
    }

    /**
     * Add permissions.
     *
     * @param id         the id
     * @param permission the permission
     */
    public void addPermissions(@NotNull String id, Permission permission) {
        User user = App.userHandler.getUserFromID(id);
        addPermissions(user, new Permission[]{permission});
    }

    /**
     * Reset permissions.
     *
     * @param user the user
     */
    public void resetPermissions(@NotNull User user) {
        user.setPermissions(new Permission[0]);

        App.userHandler.saveUser(user);
    }

    /**
     * Reset permissions.
     *
     * @param id the id
     */
    public void resetPermissions(@NotNull String id) {
        User user = App.userHandler.getUserFromID(id);
        resetPermissions(user);
    }

    /**
     * Permission array to string string.
     *
     * @param permissions the permissions
     * @return the string
     */
    public String permissionArrayToString(Permission[] permissions) {
        return gson.toJson(permissions);
    }
}
