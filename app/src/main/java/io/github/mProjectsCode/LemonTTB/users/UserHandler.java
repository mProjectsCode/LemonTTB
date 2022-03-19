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

package io.github.mProjectsCode.LemonTTB.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.IOHelper;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User handler.
 */
public class UserHandler {
    /**
     * The constant LOGGER.
     */
    public final static Logger LOGGER = Logger.getLogger(UserHandler.class);

    /**
     * The Gson.
     */
    public Gson gson;

    /**
     * Instantiates a new User handler.
     */
    public UserHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Gets user from id.
     *
     * @param id the id
     * @return the user from id
     */
    public User getUserFromID(@NotNull String id) {
        User user = null;
        File userFile = getFileFromID(id);

        if (!userFile.exists()) {
            user = new User(id);
            String userJSON = gson.toJson(user);
            try {
                IOHelper.write(userFile, userJSON);
            } catch (IOException e) {
                App.exit("Could not write user data file.", e);
            }
        } else {
            String userFileContent = IOHelper.load(userFile);
            user = gson.fromJson(userFileContent, User.class);
        }

        return user;
    }

    /**
     * Save user.
     *
     * @param user the user
     */
    public void saveUser(User user) {
        try {
            IOHelper.write(getFileFromID(user.getId()), gson.toJson(user));
        } catch (IOException e) {
            App.exit("Could not write user data file.", e);
        }
    }

    /**
     * Gets file name from id.
     *
     * @param id the id
     * @return the file name from id
     */
    public String getFileNameFromID(@NotNull String id) {
        return id.concat("-LemonTTB-user.txt");
    }

    /**
     * Gets file from id.
     *
     * @param id the id
     * @return the file from id
     */
    public File getFileFromID(@NotNull String id) {
        return new File(App.userPath, getFileNameFromID(id));
    }

    /**
     * Gets users from voice channel.
     *
     * @param channel the channel
     * @return the users from voice channel
     */
    public List<User> getUsersFromVoiceChannel(VoiceChannel channel) {
        return getUsersFromVoiceChannel(channel.getMembers());
    }

    /**
     * Gets users from voice channel.
     *
     * @param members the members
     * @return the users from voice channel
     */
    public List<User> getUsersFromVoiceChannel(List<Member> members) {
        return members.stream().map(m -> getUserFromID(m.getId())).collect(Collectors.toList());
    }
}
