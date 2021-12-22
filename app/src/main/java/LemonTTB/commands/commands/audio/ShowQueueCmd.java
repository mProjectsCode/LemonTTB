package LemonTTB.commands.commands.audio;

import LemonTTB.App;
import LemonTTB.LemonTTB_Audio.LemonTTB_AudioManager;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import LemonTTB.permissions.Permission;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class ShowQueueCmd extends Command {
    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Shows a list of all the tracks that are in the queue of the audio player.",
                new CommandDescription.ArgumentDescription[]{

                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"showQueue"};
    }

    @Override
    public @NotNull Permission[] getCommandPermissions() {
        return new Permission[] {Permission.AUDIO};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {
        AudioTrack[] audioTracks = App.audioManager.getTracksInQueue();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < audioTracks.length; i++) {
            sb.append(i).append(": ");
            sb.append(LemonTTB_AudioManager.getTitleFromAudioTrack(audioTracks[i])).append("\n");
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(255, 255, 255));
        embed.setTitle("LemonTTB audio player queue", null);
        embed.addField(
                "Currently playing: ",
                LemonTTB_AudioManager.getTitleFromAudioTrack(App.audioManager.getAudioPlayer().getPlayingTrack()),
                false
        );
        if (!Objects.equals(audioTracks.length, 0)) {
            embed.addField("Next in queue: ", sb.toString(), false);
        }

        msg.getChannel().sendMessageEmbeds(embed.build()).queue();

        Command.LOGGER.logCommand(commandObject, true, "");
    }
}
