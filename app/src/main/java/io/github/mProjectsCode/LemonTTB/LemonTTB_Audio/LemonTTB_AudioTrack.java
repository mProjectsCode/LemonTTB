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

package io.github.mProjectsCode.LemonTTB.LemonTTB_Audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * The type Lemon ttb audio track.
 */
public class LemonTTB_AudioTrack {
    /**
     * The Audio track.
     */
    public final AudioTrack audioTrack;
    /**
     * The Track data.
     */
    public final TrackData trackData;

    /**
     * Instantiates a new Lemon ttb audio track.
     *
     * @param audioTrack the audio track
     * @param trackData  the track data
     */
    public LemonTTB_AudioTrack(AudioTrack audioTrack, TrackData trackData) {
        this.audioTrack = audioTrack;
        this.trackData = trackData;
    }

    /**
     * Gets audio track.
     *
     * @return the audio track
     */
    public AudioTrack getAudioTrack() {
        return audioTrack;
    }

    /**
     * Gets track data.
     *
     * @return the track data
     */
    public TrackData getTrackData() {
        return trackData;
    }
}
