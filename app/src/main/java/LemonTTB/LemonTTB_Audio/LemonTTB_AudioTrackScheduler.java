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

package LemonTTB.LemonTTB_Audio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import LemonTTB.App;
import LemonTTB.Logger.Logger;

public class LemonTTB_AudioTrackScheduler extends AudioEventAdapter {
    private static final Logger LOGGER = Logger.getLogger(LemonTTB_AudioTrackScheduler.class);

    private final AudioPlayer audioPlayer;
    private final BlockingQueue<AudioTrack> queue;

    private boolean looping;

    /**
     * Constructor for LemonTTB_AudioTrackScheduler
     * 
     * @param audioPlayer
     */
    public LemonTTB_AudioTrackScheduler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingQueue<>();
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only
        // if nothing is currently playing. If something is playing, it returns false
        // and does nothing. In that case the player was already playing so this track
        // goes to the queue instead.
        if (!audioPlayer.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not.
        // In case queue was empty, we are giving null to startTrack, which is a valid
        // argument and will simply stop the player.
        // If the scheduler is looping start the current track angain intead.
        if (looping) {
            App.audioManager.loadAndPlayTrack(audioPlayer.getPlayingTrack().getIdentifier());
        }
        audioPlayer.startTrack(queue.poll(), false);
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack(AudioTrack finishedTrack) {
        // Start the next track, regardless of if something is already playing or not.
        // In case queue was empty, we are giving null to startTrack, which is a valid
        // argument and will simply stop the player.
        // If the scheduler is looping start the current track angain intead.
        if (looping) {
            App.audioManager.loadAndPlayTrack(finishedTrack.getIdentifier());
        }
        audioPlayer.startTrack(queue.poll(), false);
    }

    /**
     * Player was paused
     * 
     * @param player instance of the audioPlayer
     */
    @Override
    public void onPlayerPause(AudioPlayer player) {
        LOGGER.logDebug("Paused audio player on track: " + queue.peek().getInfo().identifier);
    }

    /**
     * Player was resumed
     * 
     * @param player instance of the audioPlayer
     */
    @Override
    public void onPlayerResume(AudioPlayer player) {
        LOGGER.logDebug("Resumed audio player on track: " + queue.peek().getInfo().identifier);
    }

    /**
     * A track started playing
     * 
     * @param player instance of the audioPlayer
     * @param track  the track that started playing
     */
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        LOGGER.logDebug("Audio player started playing: " + track.getInfo().identifier);
    }

    /**
     * The current track ended
     * 
     * @param player    instance of the audioPlayer
     * @param track     the track that ended
     * @param endReason The reason why it ended
     *                  FINISHED: A track finished or died by an exception
     *                  (mayStartNext = true).
     *                  LOAD_FAILED: Loading of a track failed (mayStartNext =
     *                  true).
     *                  STOPPED: The player was stopped.
     *                  REPLACED: Another track started playing while this had not
     *                  finished
     *                  CLEANUP: Player hasn't been queried for a while, if you want
     *                  you can put a clone of this back to your queue
     */
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        LOGGER.logDebug("Audio player track ended: " + track.getInfo().identifier);

        if (endReason.mayStartNext) {
            nextTrack(track);
        }
    }

    /**
     * An already playing track threw an exception (track end event will still be
     * received separately)
     */
    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        LOGGER.logDebug("Audio player track exception: " + track.getInfo().identifier);
    }

    /**
     * Audio track has been unable to provide any audio, might want to just start
     * a new track.
     */
    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        LOGGER.logDebug("Audio player track stuck: " + track.getInfo().identifier);
        nextTrack(track);
    }

}
