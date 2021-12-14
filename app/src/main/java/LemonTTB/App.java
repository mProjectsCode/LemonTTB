/*******************************************************************************
 * 
 * LemonTTB: A discord bot for helping the GM with running table top sessions using discord for comuniction.
 * (C) Copyright 2021
 * Programmed by Moritz Jung
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/

package LemonTTB;

import java.io.File;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import com.google.common.io.Resources;

import LemonTTB.LemonTTB_Audio.LemonTTB_AudioManager;
import LemonTTB.commands.CommandHandler;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static final String VERSION = "v0.0.1";

    public static File configPath;
    public static File audioPath;
    public static String resourcePath;
    public static JDA jda;
    public static LemonTTB_AudioManager audioManager;

    public static void main(String[] args) {
        audioManager = new LemonTTB_AudioManager();
        audioManager.createPlayer();

        resourcePath = Resources.getResource("").getPath() + "/resources";

        Logger.logFilePath = new File(resourcePath, "/logs").getPath();
        Logger.debug = true;

        configPath = new File(resourcePath, "/config/botConfig.txt");
        LOGGER.logDebug("Config Path: " + configPath.getPath());
        Config.load(configPath);

        audioPath = new File(resourcePath, "/music");

        JDABuilder builder = JDABuilder.createDefault(Config.options.token);

        // Disable parts of the cache
        // builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching(Config.options.prefix + "help"));

        builder.addEventListeners(new CommandHandler());

        try {
            jda = builder.build();
        } catch (LoginException e) {
            exit("Could not log in.", e);
        }

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            exit("Interruption while waiting for jda to ready.");
        }
    }

    public static void exit(String message) {
        LOGGER.logWarning("Programm is exiting with message: ");
        LOGGER.logWarning(message);
        System.exit(0);
    }

    public static void exit(String message, Exception e) {
        LOGGER.logWarning("Programm is exiting with message: ");
        LOGGER.logWarning(message);
        LOGGER.logError(e);
        System.exit(0);
    }
}
