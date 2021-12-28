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
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import io.github.mProjectsCode.LemonTTB.events.payloads.payloads.AudioPlayerPayload;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type Lemon ttb audio track scheduler.
 */
public class LemonTTB_AudioTrackScheduler extends AudioEventAdapter {
    private static final Logger LOGGER = Logger.getLogger(LemonTTB_AudioTrackScheduler.class);

    private final AudioPlayer audioPlayer;
    private final BlockingQueue<LemonTTB_AudioTrack> queue;

    private LemonTTB_AudioTrack currentTrack;
    private boolean looping;

    /**
     * Constructor for LemonTTB_AudioTrackScheduler
     *
     * @param audioPlayer the audio player
     */
    public LemonTTB_AudioTrackScheduler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingQueue<>();
        this.currentTrack = null;
        this.looping = false;
    }

    /**
     * Gets current track.
     *
     * @return the current track
     */
    public LemonTTB_AudioTrack getCurrentTrack() {
        return currentTrack;
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
     * Sets looping.
     *
     * @param looping the looping
     */
    public void setLooping(boolean looping) {
        this.looping = looping;

        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Switched looping to " + looping,
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.SWITCHED_LOOP_PLAYER),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));
    }

    /**
     * Getter for the queue.
     *
     * @return the queue
     */
    public BlockingQueue<LemonTTB_AudioTrack> getQueue() {
        return queue;
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(LemonTTB_AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only
        // if nothing is currently playing. If something is playing, it returns false
        // and does nothing. In that case the player was already playing so this track
        // goes to the queue instead.
        LOGGER.logDebug("Queued new track: " + track.getTrackData().getName());

        AudioTrack playingTrack = audioPlayer.getPlayingTrack();
        if (Objects.equals(playingTrack, null)) {
            currentTrack = track;
            audioPlayer.startTrack(track.getAudioTrack(), false);
            LOGGER.logDebug("Started track directly");
        } else {
            queue.offer(track);
            LOGGER.logDebug("Put track in queue");
        }

        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Queues new Audio Track",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.QUEUED_TRACK),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track            The track to play or add to queue.
     * @param audioTrackSource the audio track source
     */
    public void queue(AudioTrack track, AudioTrackSource audioTrackSource) {
        queue(new LemonTTB_AudioTrack(track, new TrackData(track, audioTrackSource)));
    }

    /**
     * Clear queue.
     */
    public void clearQueue() {
        LOGGER.logDebug("Cleared queue");

        queue.clear();

        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Cleared the queue",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.EMPTIED_QUEUE),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        // Start the next track.
        // If the scheduler is looping start the current track again instead.
        if (looping) {
            LOGGER.logDebug("Adding current track back to queue: " + currentTrack.getTrackData().getName());
            App.audioManager.loadAndPlayTrack(currentTrack.getAudioTrack().getIdentifier(), currentTrack.getTrackData().getAudioTrackSource());
        }
        forceNextTrack();
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     *
     * @param finishedTrack the finished track
     */
    public void nextTrack(LemonTTB_AudioTrack finishedTrack) {
        // Start the next track.
        // If the scheduler is looping start the current track again instead.
        if (looping) {
            LOGGER.logDebug("Adding finished track back to queue: " + finishedTrack.getTrackData().getName());
            App.audioManager.loadAndPlayTrack(finishedTrack.getAudioTrack().getIdentifier(), finishedTrack.getTrackData().getAudioTrackSource());
        }
        forceNextTrack();
    }

    /**
     * Force next track.
     */
    public void forceNextTrack() {
        LOGGER.logDebug("Forcing next track");

        LemonTTB_AudioTrack track = queue.poll();
        if (!Objects.equals(track, null)) {
            LOGGER.logDebug("Forcing next track -> Started track: " + track.getTrackData().getName());
            currentTrack = track;
            audioPlayer.startTrack(track.audioTrack, false);
        } else {
            audioPlayer.stopTrack();
        }
    }

    /**
     * Player was paused
     *
     * @param player instance of the audioPlayer
     */
    @Override
    public void onPlayerPause(AudioPlayer player) {
        LOGGER.logDebug("Paused audio player on track: " + (queue.peek() != null ? queue.peek().getTrackData().getName() : ""));
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Paused the audio player",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.SWITCHED_PAUSE_PLAYER),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));
    }

    /**
     * Player was resumed
     *
     * @param player instance of the audioPlayer
     */
    @Override
    public void onPlayerResume(AudioPlayer player) {
        LOGGER.logDebug("Resumed audio player on track: " + (queue.peek() != null ? queue.peek().getTrackData().getName() : ""));
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Unpaused the audio player",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.SWITCHED_PAUSE_PLAYER),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));
    }

    /**
     * A track started playing
     *
     * @param player instance of the audioPlayer
     * @param track  the track that started playing
     */
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        LOGGER.logDebug("Audio player started playing: " + currentTrack.getTrackData().getName());
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Started a new track",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.STARTED_TRACK),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));
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
        LOGGER.logDebug("Audio player track ended: " + currentTrack.getTrackData().getName());
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Finished track",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.FINISHED_TRACK),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));

        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    /**
     * An already playing track threw an exception (track end event will still be
     * received separately)
     */
    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        LOGGER.logDebug("Audio player track exception: " + currentTrack.getTrackData().getName());
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Track threw an exception",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.TRACK_ERROR),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));
    }

    /**
     * Audio track has been unable to provide any audio, might want to just start
     * a new track.
     */
    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        LOGGER.logDebug("Audio player track stuck: " + track.getInfo().identifier);
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Track got stuck",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.TRACK_STUCK),
                LemonTTB_AudioTrackScheduler.class.getName()
        ));

        forceNextTrack();
    }

}
