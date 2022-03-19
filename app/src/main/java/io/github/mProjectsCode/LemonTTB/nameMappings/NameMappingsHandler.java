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

package io.github.mProjectsCode.LemonTTB.nameMappings;

import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.users.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Name mappings handler.
 */
public class NameMappingsHandler {
    /**
     * Create name mapping.
     *
     * @param username the username
     * @param name     the name
     */
    public void createNameMapping(String username, String name) {
        Config.options.nameMappings.put(username, name);
        Config.save();
    }

    /**
     * Delete name mapping string.
     *
     * @param username the username
     * @return the string
     */
    public String deleteNameMapping(String username) {
        String name = Config.options.nameMappings.remove(username);
        Config.save();
        return name;
    }

    /**
     * Delete name mapping for name string.
     *
     * @param name the name
     * @return the string
     */
    public String deleteNameMappingForName(String name) {
        String name1 = Config.options.nameMappings.remove(getUsernameFromName(name));
        Config.save();
        return name1;
    }

    /**
     * Gets username from name.
     *
     * @param name the name
     * @return the username from name
     */
    public String getUsernameFromName(String name) {
        String username = null;
        for (Map.Entry<String, String> entry : Config.options.nameMappings.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Gets mappings by users.
     *
     * @param users the users
     * @return the mappings by users
     */
    public Map<String, String> getMappingsByUsers(List<User> users) {
        return getMappingsByIds(users.stream().map(User::getId).collect(Collectors.toList()));
    }

    /**
     * Gets mappings by ids.
     *
     * @param ids the ids
     * @return the mappings by ids
     */
    public Map<String, String> getMappingsByIds(List<String> ids) {
        return Config.options.nameMappings.entrySet().stream().filter(e -> ids.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
