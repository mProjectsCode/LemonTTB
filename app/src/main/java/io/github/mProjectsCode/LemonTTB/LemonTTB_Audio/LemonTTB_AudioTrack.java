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
import org.jetbrains.annotations.NotNull;

/**
 * The type Lemon ttb audio track.
 */
public class LemonTTB_AudioTrack {
    /**
     * The Audio track.
     */
    @NotNull
    public final AudioTrack audioTrack;
    /**
     * The Track data.
     */
    @NotNull
    public final TrackData trackData;

    /**
     * Instantiates a new Lemon ttb audio track.
     *
     * @param audioTrack the audio track
     * @param trackData  the track data
     */
    public LemonTTB_AudioTrack(@NotNull AudioTrack audioTrack, @NotNull TrackData trackData) {
        this.audioTrack = audioTrack;
        this.trackData = trackData;
    }

    /**
     * Gets audio track.
     *
     * @return the audio track
     */
    @NotNull
    public AudioTrack getAudioTrack() {
        return audioTrack;
    }

    /**
     * Gets track data.
     *
     * @return the track data
     */
    @NotNull
    public TrackData getTrackData() {
        return trackData;
    }

    /**
     * Update position.
     */
    public void updatePosition() {
        trackData.setPosition(((int) audioTrack.getPosition()) / 1000);
    }

    /**
     * Make playable clone lemon ttb audio track.
     *
     * @return the lemon ttb audio track
     */
    public LemonTTB_AudioTrack makePlayableClone() {
        AudioTrack track = audioTrack;
        return new LemonTTB_AudioTrack(track, new TrackData(track, trackData.getAudioTrackSource()));
    }
}
