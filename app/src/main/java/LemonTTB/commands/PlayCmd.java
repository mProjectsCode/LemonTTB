package LemonTTB.commands;

import java.io.File;

import LemonTTB.App;
import LemonTTB.CommandObject;
import net.dv8tion.jda.api.entities.Message;

public class PlayCmd extends Command {

    @Override
    public void run(CommandObject commandObject, Message msg) {
        // LOGGER.logDebug(Boolean.toString((new File(App.audioPath, "/Into the Mists
        // E.mp3")).exists()));

        App.audioManager.loadAndPlay(new File(App.audioPath, "/Into the Mists E.mp3").getPath());

        super.LOGGER.logCommand(commandObject, true, "");
    }

}
