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

package io.github.mProjectsCode.LemonTTB.LemonTTB_Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

/**
 * The type Lemon ttb audio send handler.
 */
public class LemonTTB_AudioSendHandler implements AudioSendHandler {
    private static final Logger LOGGER = Logger.getLogger(LemonTTB_AudioSendHandler.class);

    private final AudioPlayer audioPlayer;
    private final ByteBuffer buffer;
    private final MutableAudioFrame frame;

    /**
     * Instantiates a new audio send handler.
     *
     * @param audioPlayer the audio player
     */
    public LemonTTB_AudioSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
        // returns true if audio was provided
        return audioPlayer.provide(frame);
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        // flip to make it a read buffer
        buffer.flip();
        return buffer;
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
