package LemonTTB.commands.commands;

import java.util.List;
import java.util.Objects;

import LemonTTB.App;
import LemonTTB.commands.Command;
import LemonTTB.commands.CommandDescription;
import LemonTTB.commands.CommandObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

public class MoveCmd extends Command {

    @Override
    public @NotNull CommandDescription getCommandDescription() {
        return new CommandDescription(
                "Moves a people fom one channel to another.",
                new CommandDescription.ArgumentDescription[]{
                        new CommandDescription.ArgumentDescription(
                                "-b",
                                "boolean",
                                false,
                                "Whether to set looping to ture or false."),
                        new CommandDescription.ArgumentDescription(
                                "-a",
                                "",
                                false,
                                "Whether to move everyone. If -u is also present, -u will act as a blacklist."),
                        new CommandDescription.ArgumentDescription(
                                "-u",
                                "string",
                                false,
                                "Specifies a specific user to be moved.")
                }
        );
    }

    @Override
    public @NotNull String[] getCommandIdentifiers() {
        return new String[] {"move"};
    }

    @Override
    public void run(CommandObject commandObject, Message msg) {

        // TODO: actually implement those into the config
        String primaryChannelId = "299960527701016576";
        String secondaryChannelId = "825073596136292422";

        VoiceChannel primaryChannel = null;
        VoiceChannel secondaryChannel = null;

        List<Guild> guilds = App.jda.getGuilds();
        for (int i = 0; i < guilds.size(); i++) {
            VoiceChannel channel = guilds.get(i).getVoiceChannelById(primaryChannelId);
            primaryChannel = Objects.equals(channel, null) ? primaryChannel : channel;

            VoiceChannel channel2 = guilds.get(i).getVoiceChannelById(secondaryChannelId);
            secondaryChannel = Objects.equals(channel2, null) ? secondaryChannel : channel2;
        }

        if (Objects.equals(primaryChannel, null)) {
            Command.LOGGER.logWarning("Primary channel not found.");
            return;
        }
        if (Objects.equals(secondaryChannel, null)) {
            Command.LOGGER.logWarning("Secondary channel not found.");
            return;
        }

        VoiceChannel moveToChannel = secondaryChannel;
        VoiceChannel moveFromChannel = primaryChannel;

        CommandObject.Argument userArgument = commandObject.getArgument("-u");
        CommandObject.Argument allArgument = commandObject.getArgument("-a");
        CommandObject.Argument backArgument = commandObject.getArgument("-b");

        if (!Objects.equals(backArgument, null)) {
            moveToChannel = Boolean.parseBoolean(backArgument.value) ? secondaryChannel : primaryChannel;
            moveFromChannel = Boolean.parseBoolean(backArgument.value) ? primaryChannel : secondaryChannel;
        }
        Command.LOGGER.logTrace("Move to channel: " + moveToChannel.getName());
        Command.LOGGER.logTrace("Move from channel: " + moveFromChannel.getName());

        if (!Objects.equals(allArgument, null)) {
            List<Member> members = moveFromChannel.getMembers();
            for (int i = 0; i < members.size(); i++) {
                moveFromChannel.getGuild().moveVoiceMember(members.get(i), moveToChannel).queue();
            }
        } else if (!Objects.equals(userArgument, null)) {
            if (!Objects.equals(userArgument.value, null)) {
                List<Member> members = moveFromChannel.getMembers();
                for (int i = 0; i < members.size(); i++) {
                    if (Objects.equals(members.get(i).getNickname(), userArgument.value)) {
                        moveFromChannel.getGuild().moveVoiceMember(members.get(i), moveToChannel).queue();
                    }
                }
            }
        }

    }

}
