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

package io.github.mProjectsCode.LemonTTB.events.payloads.payloads;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.events.payloads.Payload;
import io.github.mProjectsCode.LemonTTB.events.payloads.PayloadResponse;
import io.github.mProjectsCode.LemonTTB.users.User;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Voice channel payload.
 */
public class VoiceChannelPayload implements Payload {
    private final VoiceChannelPayloadData data;
    private final PayloadResponse response = PayloadResponse.OK;

    /**
     * Instantiates a new Voice channel payload.
     */
    public VoiceChannelPayload() {
        this.data = new VoiceChannelPayloadData();
    }

    @Override
    public String getResponse() {
        return response.name();
    }

    @Override
    public VoiceChannelPayloadData getData() {
        return data;
    }

    /**
     * The type Voice channel payload data.
     */
    public static class VoiceChannelPayloadData {
        private final String guildName;
        private final String primaryChannelName;
        private final String secondaryChannelName;
        private final List<VoiceChannelPayloadDataUser> primaryChannelUsers;
        private final List<VoiceChannelPayloadDataUser> secondaryChannelUsers;


        /**
         * Instantiates a new Voice channel payload data.
         */
        public VoiceChannelPayloadData() {
            this.primaryChannelUsers = new ArrayList<>();
            this.secondaryChannelUsers = new ArrayList<>();

            GuildChannel primaryChannel = App.jda.getGuildChannelById(Config.options.primaryVoiceChannel);
            GuildChannel secondaryChannel = App.jda.getGuildChannelById(Config.options.secondaryVoiceChannel);

            assert primaryChannel != null;
            assert secondaryChannel != null;

            this.guildName = primaryChannel.getGuild().getName();
            this.primaryChannelName = primaryChannel.getName();
            this.secondaryChannelName = secondaryChannel.getName();

            List<Member> primaryChannelMembers = primaryChannel.getMembers();
            List<Member> secondaryChannelMembers = secondaryChannel.getMembers();

            List<User> primaryChannelUsers = App.userHandler.getUsersFromVoiceChannel(primaryChannelMembers);
            List<User> secondaryChannelUsers = App.userHandler.getUsersFromVoiceChannel(secondaryChannelMembers);

            Map<String, String> primaryChannelUserNames = App.nameMappingsHandler.getMappingsByUsers(primaryChannelUsers);
            Map<String, String> secondaryChannelUserNames = App.nameMappingsHandler.getMappingsByUsers(secondaryChannelUsers);

            for (int i = 0; i < primaryChannelMembers.size(); i++) {
                String name = primaryChannelUserNames.get(primaryChannelUsers.get(i).getId());
                name = Objects.equals(name, null) ? primaryChannelMembers.get(i).getEffectiveName() : name;

                this.primaryChannelUsers.add(new VoiceChannelPayloadDataUser(
                        primaryChannelUsers.get(i),
                        name,
                        primaryChannelMembers.get(i).getVoiceState().isGuildMuted(),
                        primaryChannelMembers.get(i).getVoiceState().isGuildDeafened()
                ));
            }

            for (int i = 0; i < secondaryChannelMembers.size(); i++) {
                String name = secondaryChannelUserNames.get(secondaryChannelUsers.get(i).getId());
                name = Objects.equals(name, null) ? secondaryChannelMembers.get(i).getEffectiveName() : name;

                this.secondaryChannelUsers.add(new VoiceChannelPayloadDataUser(
                        secondaryChannelUsers.get(i),
                        name,
                        secondaryChannelMembers.get(i).getVoiceState().isMuted(),
                        secondaryChannelMembers.get(i).getVoiceState().isDeafened()
                ));
            }
        }

        /**
         * Gets guild name.
         *
         * @return the guild name
         */
        public String getGuildName() {
            return guildName;
        }

        /**
         * Gets primary channel name.
         *
         * @return the primary channel name
         */
        public String getPrimaryChannelName() {
            return primaryChannelName;
        }

        /**
         * Gets secondary channel name.
         *
         * @return the secondary channel name
         */
        public String getSecondaryChannelName() {
            return secondaryChannelName;
        }

        /**
         * Gets primary channel users.
         *
         * @return the primary channel users
         */
        public List<VoiceChannelPayloadDataUser> getPrimaryChannelUsers() {
            return primaryChannelUsers;
        }

        /**
         * Gets secondary channel users.
         *
         * @return the secondary channel users
         */
        public List<VoiceChannelPayloadDataUser> getSecondaryChannelUsers() {
            return secondaryChannelUsers;
        }

        /**
         * The type Voice channel payload data user.
         */
        public static class VoiceChannelPayloadDataUser {
            private final User user;
            private final String name;
            private final boolean muted;
            private final boolean deafened;

            /**
             * Instantiates a new Voice channel payload data user.
             *
             * @param user     the user
             * @param name     the name
             * @param muted    the muted
             * @param deafened the deafened
             */
            public VoiceChannelPayloadDataUser(User user, String name, boolean muted, boolean deafened) {
                this.user = user;
                this.name = name;
                this.muted = muted;
                this.deafened = deafened;
            }

            /**
             * Gets user.
             *
             * @return the user
             */
            public User getUser() {
                return user;
            }

            /**
             * Gets name.
             *
             * @return the name
             */
            public String getName() {
                return name;
            }

            /**
             * Is muted boolean.
             *
             * @return the boolean
             */
            public boolean isMuted() {
                return muted;
            }

            /**
             * Is deafened boolean.
             *
             * @return the boolean
             */
            public boolean isDeafened() {
                return deafened;
            }
        }
    }
}
