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
 * The type Track data.
 */
public class TrackData {
    private final String name;
    private final int length;
    private final AudioTrackSource audioTrackSource;
    private int position;

    /**
     * Instantiates a new Track data.
     *
     * @param audioTrack       the audio track
     * @param audioTrackSource the audio track source
     */
    public TrackData(AudioTrack audioTrack, AudioTrackSource audioTrackSource) {
        this.name = LemonTTB_AudioManager.getTitleFromAudioTrack(audioTrack);
        this.length = ((int) audioTrack.getDuration()) / 1000;
        this.position = 0;
        this.audioTrackSource = audioTrackSource;
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
     * Gets length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets audio track source.
     *
     * @return the audio track source
     */
    public AudioTrackSource getAudioTrackSource() {
        return audioTrackSource;
    }
}
