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
 *
 */

package io.github.mProjectsCode.LemonTTB.events.payloads.payloads;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.LemonTTB_Audio.TrackData;
import io.github.mProjectsCode.LemonTTB.events.payloads.Payload;

/**
 * The type Audio player payload.
 */
public class AudioPlayerPayload implements Payload {
    private final AudioPlayerPayloadData data;
    private final AudioPlayerPayloadResponse response;

    /**
     * Instantiates a new Audio player payload.
     *
     * @param response the response
     */
    public AudioPlayerPayload(AudioPlayerPayloadResponse response) {
        this.data = new AudioPlayerPayloadData();
        this.response = response;
    }

    @Override
    public String getResponse() {
        return response.name();
    }

    @Override
    public Object getData() {
        return data;
    }

    /**
     * The enum Audio player payload response.
     */
    public enum AudioPlayerPayloadResponse {
        /**
         * Queued track audio player payload response.
         */
        QUEUED_TRACK,
        /**
         * Started track audio player payload response.
         */
        STARTED_TRACK,
        /**
         * Skipped track audio player payload response.
         */
        SKIPPED_TRACK,
        /**
         * Finished track audio player payload response.
         */
        FINISHED_TRACK,
        /**
         * Paused player audio player payload response.
         */
        PAUSED_PLAYER,
        /**
         * Unpaused player audio player payload response.
         */
        UNPAUSED_PLAYER,
        /**
         * Track error audio player payload response.
         */
        TRACK_ERROR,
        /**
         * Track stuck audio player payload response.
         */
        TRACK_STUCK,
        /**
         * Emptied queue audio player payload response.
         */
        EMPTIED_QUEUE,
        /**
         * Status request audio player payload response.
         */
        STATUS_REQUEST
    }

    /**
     * The type Audio player payload data.
     */
    public static class AudioPlayerPayloadData {
        /**
         * The Current tack.
         */
        public final TrackData currentTack;
        /**
         * The Queue.
         */
        public final TrackData[] queue;
        /**
         * The Looping.
         */
        public final boolean looping;
        /**
         * The Paused.
         */
        public final boolean paused;
        /**
         * The Guild name.
         */
        public final String guildName;
        /**
         * The Channel name.
         */
        public final String channelName;

        /**
         * Instantiates a new Audio player payload data.
         */
        public AudioPlayerPayloadData() {
            this.queue = App.audioManager.getTracksInQueueAsTrackData();
            this.currentTack = App.audioManager.getCurrentTrack().getTrackData();
            this.looping = App.audioManager.isLooping();
            this.paused = App.audioManager.isPaused();
            this.guildName = App.audioManager.getChannel().getGuild().getName();
            this.channelName = App.audioManager.getChannel().getName();
        }

        /**
         * Gets current tack.
         *
         * @return the current tack
         */
        public TrackData getCurrentTack() {
            return currentTack;
        }

        /**
         * Get queue track data [ ].
         *
         * @return the track data [ ]
         */
        public TrackData[] getQueue() {
            return queue;
        }

        /**
         * Is looping boolean.
         *
         * @return the boolean
         */
        public boolean isLooping() {
            return looping;
        }

        /**
         * Is paused boolean.
         *
         * @return the boolean
         */
        public boolean isPaused() {
            return paused;
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
         * Gets channel name.
         *
         * @return the channel name
         */
        public String getChannelName() {
            return channelName;
        }
    }
}
