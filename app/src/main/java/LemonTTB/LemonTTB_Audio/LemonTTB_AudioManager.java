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

import LemonTTB.Config;
import LemonTTB.Logger.Logger;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.File;
import java.util.Objects;

public class LemonTTB_AudioManager {
    private static final Logger LOGGER = Logger.getLogger(LemonTTB_AudioManager.class);

    public static AudioPlayerManager audioPlayerManager;
    private GuildChannel channel;
    private AudioManager audioManager;
    private AudioPlayer audioPlayer;
    private LemonTTB_AudioTrackScheduler audioTrackScheduler;

    /**
     * Connects to a voice channel.
     *
     * @param channel the channel to connect to
     * @param guild   the guild of the channel
     */
    public void connect(GuildChannel channel, Guild guild) {
        // if the audioPlayer has not been initialized, log an error and do nothing
        if (Objects.equals(audioPlayer, null)) {
            LOGGER.logError("Can not join a voice channel, while audioPlayer is null."
                    + " Try calling \"createPlayer()\" once before connecting to a voice channel.");
            return;
        }

        // get the audio manager from the guild
        audioManager = guild.getAudioManager();
        // set the sending handler to our sending handler
        audioManager.setSendingHandler(new LemonTTB_AudioSendHandler(audioPlayer));
        // open an audio connection to the voice channel
        audioManager.openAudioConnection((VoiceChannel) channel);

        audioManager.setSelfDeafened(true);

        this.channel = channel;
    }

    /**
     * Leaves the voice channel.
     */
    public void leave() {
        // close the connection
        audioManager.closeAudioConnection();
        // unset the audioManager
        audioManager = null;
    }

    /**
     * Returns the channel the audio manager is connected to.
     * Returns null if not connected.
     *
     * @return the channel
     */
    public GuildChannel getChannel() {
        return channel;
    }

    /**
     * Creates an audioPlayer. Needs to be called, before it can connect to a voice
     * channel
     */
    public void createPlayer() {
        // create a new audioPlayerManager
        audioPlayerManager = new DefaultAudioPlayerManager();
        // register the audioPlayerManager as a local source
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        // create a new audioPlayer
        audioPlayer = audioPlayerManager.createPlayer();
        // create instance of our track scheduler
        audioTrackScheduler = new LemonTTB_AudioTrackScheduler(audioPlayer);
        // add our track scheduler to the audioPlayer
        audioPlayer.addListener(audioTrackScheduler);

        audioPlayer.setVolume(Config.options.defaultVolume);
        LOGGER.logDebug("Set volume to the config default of " + Config.options.defaultVolume);
    }

    /**
     * Load and play a audioTrack based on a path to the track. Throws a runtime
     * exception when the path is not pointing to a file or is invalid.
     *
     * @param trackPath the path to the track
     */
    public void loadAndPlayTrack(final String trackPath) {
        // load track
        audioPlayerManager.loadItemOrdered(this, trackPath, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                // when the track successfully loaded queue it
                LOGGER.logInfo("Adding to queue " + track.getInfo().identifier);
                queueToTrackScheduler(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    // queue every track in the playlist
                    queueToTrackScheduler(track);
                }
            }

            @Override
            public void noMatches() {
                // audioPlayerManager found no matches
                // this should not happen since we should only pass a valid path to this
                LOGGER.logWarning("Audio Manager found no matches for: " + trackPath);
            }

            @Override
            public void loadFailed(FriendlyException e) {
                // somehow the loading of the track failed
                LOGGER.logError("Audio Manager failed to load track: " + trackPath);
                LOGGER.logError(e);
            }
        });
    }

    /**
     * Add a audioTrack to the queue of the audioTrackScheduler.
     *
     * @param audioTrack the audio track to queue
     */
    private void queueToTrackScheduler(AudioTrack audioTrack) {
        // play a loaded audio track
        audioTrackScheduler.queue(audioTrack);
    }

    /**
     * Skip the current track of the audioTrackScheduler.
     */
    public void skipTrack() {
        audioTrackScheduler.nextTrack();
    }

    /**
     * Clears the queue of the audioTrackScheduler.
     */
    public void clearQueue() {
        audioTrackScheduler.clearQueue();
    }

    public AudioTrack[] getTracksInQueue() {
        return audioTrackScheduler.getQueue().toArray(new AudioTrack[0]);
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    /**
     * Get the player volume.
     *
     * @return volume
     */
    public int getVolume() {
        return audioPlayer.getVolume();
    }

    /**
     * Set the player volume.
     *
     * @param volume volume
     */
    public void setVolume(int volume) {
        audioPlayer.setVolume(volume);
    }

    /**
     * Is the player looping.
     *
     * @return looping
     */
    public boolean isLooping() {
        return audioTrackScheduler.isLooping();
    }

    /**
     * Set the player to loop.
     *
     * @param looping looping
     */
    public void setLooping(boolean looping) {
        audioTrackScheduler.setLooping(looping);
    }

    /**
     * Is the player paused.
     *
     * @return paused
     */
    public boolean isPaused() {
        return audioPlayer.isPaused();
    }

    /**
     * Set the pause state of the player.
     *
     * @param paused paused
     */
    public void setPaused(boolean paused) {
        audioPlayer.setPaused(paused);
    }

    /**
     * Wrapper around AudioPlayer to use it as an AudioSendHandler.
     *
     * @return LemonTTB_AudioSendHandler
     */
    public LemonTTB_AudioSendHandler getSendHandler() {
        return new LemonTTB_AudioSendHandler(audioPlayer);
    }

    public static String getTitleFromAudioTrack(AudioTrack track) {
        if (Objects.equals(track, null)) {
            return "Currently not a track.";
        }

        String title = track.getInfo().title;
        if (Objects.equals(title, "Unknown title")) {
            title = new File(track.getIdentifier()).getName();
        }
        return title;
    }
}