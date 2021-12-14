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

import java.io.File;
import java.util.Objects;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import LemonTTB.Logger.Logger;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

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

        // get the audiomanager from the guild
        audioManager = guild.getAudioManager();
        // set the sendinghandler to our sendinghandler
        audioManager.setSendingHandler(new LemonTTB_AudioSendHandler(audioPlayer));
        // open an autioconnection to the voicechannel
        audioManager.openAudioConnection((VoiceChannel) channel);
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
     * Creates an audioPlayer. Needs to be called, before it can connect to a voice
     * channel
     */
    public void createPlayer() {
        // create a new audioPlayerManager
        audioPlayerManager = new DefaultAudioPlayerManager();
        // register the audioPlayerManager as a local source
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        // create a new audioPlayer
        audioPlayer = audioPlayerManager.createPlayer();
        // create instance of our track scheduler
        audioTrackScheduler = new LemonTTB_AudioTrackScheduler(audioPlayer);
        // add our track sheduler to the audioPlayer
        audioPlayer.addListener(audioTrackScheduler);
    }

    /**
     * Load and play a audioTrack based on a path to the track. Throws a runtrime
     * exception when the path is not pointing to a file or is invalid.
     * 
     * @param trackPath the path to the track
     */
    public void loadAndPlayTrack(final String trackPath) {
        if (!new File(trackPath).exists()) {
            throw new RuntimeException("Invalid trackpath supplied to loadAndPlayTrack of " + this.toString());
        }

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
                // this should not happen since we schould only pass a valid path to this
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
     * @param audioTrack
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
     * Wrapper around AudioPlayer to use it as an AudioSendHandler.
     * 
     * @return LemonTTB_AudioSendHandler
     */
    public LemonTTB_AudioSendHandler getSendHandler() {
        return new LemonTTB_AudioSendHandler(audioPlayer);
    }
}
