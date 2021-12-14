package LemonTTB.commands.commands;

import java.io.Console;
import java.util.List;
import java.util.Objects;

import org.checkerframework.checker.units.qual.m;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class JoinCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {
        List<Guild> guilds = App.jda.getGuilds();

        for (int i = 0; i < guilds.size(); i++) {
            LOGGER.logDebug(guilds.get(i).getName());
            List<GuildChannel> channels = guilds.get(i).getChannels();
            guilds.get(i).loadMembers();

            for (int j = 0; j < channels.size(); j++) {

                if (Objects.equals(channels.get(j).getType(), ChannelType.VOICE)) {
                    LOGGER.logDebug(channels.get(j).getName());
                    List<Member> members = channels.get(j).getMembers();
                    LOGGER.logDebug(Integer.toString(members.size()));

                    for (int k = 0; k < members.size(); k++) {
                        LOGGER.logDebug(members.get(k).getUser().getName());

                        if (Objects.equals(members.get(k).getId(), msg.getAuthor().getId())) {
                            App.audioManager.connect(channels.get(j), guilds.get(i));
                        }
                    }
                }
            }
        }

        super.LOGGER.logCommand(commandObject, true, "");
    }

}
