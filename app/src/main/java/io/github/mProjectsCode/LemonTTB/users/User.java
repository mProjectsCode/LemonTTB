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

package io.github.mProjectsCode.LemonTTB.users;

import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.permissions.Permission;

/**
 * The type User.
 */
public class User {
    /**
     * The constant LOGGER.
     */
    public final static Logger LOGGER = Logger.getLogger(User.class);

    /**
     * The Default permissions.
     */
    public final Permission[] defaultPermissions = new Permission[]{Permission.COMMAND};
    private Permission[] permissions;
    private String id;

    /**
     * Instantiates a new User.
     *
     * @param id the id
     */
    public User(String id) {
        this.id = id;
        this.permissions = new Permission[0];
    }

    /**
     * Get permissions permission [ ].
     *
     * @return the permission [ ]
     */
    public Permission[] getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(Permission[] permissions) {
        this.permissions = permissions;
    }

    /**
     * Has permission boolean.
     *
     * @param permission the permission
     * @return the boolean
     */
    public boolean hasPermission(Permission permission) {
        for (Permission permission1 : defaultPermissions) {
            if (permission1.equals(permission) || permission1.value > permission.value) {
                return true;
            }
        }

        for (Permission permission1 : permissions) {
            if (permission1.equals(permission) || permission1.value > permission.value) {
                return true;
            }
        }

        return false;
    }

    /**
     * Has permissions boolean.
     *
     * @param permissions the permissions
     * @return the boolean
     */
    public boolean hasPermissions(Permission[] permissions) {
        for (Permission permission1 : permissions) {
            if (!hasPermission(permission1)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
}
