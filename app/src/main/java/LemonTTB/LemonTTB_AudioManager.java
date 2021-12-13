package LemonTTB;

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

public class LemonTTB_AudioManager {
    private static final Logger LOGGER = Logger.getLogger(LemonTTB_AudioManager.class);

    public static AudioPlayerManager audioPlayerManager;
    private GuildChannel channel;
    private AudioManager audioManager;
    private AudioPlayer audioPlayer;
    private LemonTTB_AudioTrackScheduler audioTrackScheduler;

    public void connect(GuildChannel channel, Guild guild) {
        audioManager = guild.getAudioManager();
        audioManager.setSendingHandler(new LemonTTB_AudioSendHandler(audioPlayer));
        audioManager.openAudioConnection((VoiceChannel) channel);
    }

    public void createPlayer() {
        audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        audioPlayer = audioPlayerManager.createPlayer();
        audioTrackScheduler = new LemonTTB_AudioTrackScheduler(audioPlayer);
        audioPlayer.addListener(audioTrackScheduler);

    }

    public void loadAndPlay(final String trackPath) {

        audioPlayerManager.loadItemOrdered(this, trackPath, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                LOGGER.logInfo("Adding to queue " + track.getInfo().identifier);

                play(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    audioTrackScheduler.queue(track);
                    audioPlayer.playTrack(track);
                }
            }

            @Override
            public void noMatches() {
                // TODO Auto-generated method stub

            }

            @Override
            public void loadFailed(FriendlyException exception) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void play(AudioTrack audioTrack) {
        audioTrackScheduler.queue(audioTrack);
    }

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
