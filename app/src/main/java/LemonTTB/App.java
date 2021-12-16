/* 
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
 */

package LemonTTB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.security.auth.login.LoginException;

import com.google.common.io.Resources;

import LemonTTB.LemonTTB_Audio.LemonTTB_AudioManager;
import LemonTTB.Logger.Logger;
import LemonTTB.commands.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

/*
 *   _                              _______ _______ ____  
 * | |                     ___/)  |__   __|__   __|  _ \ 
 * | |     _ __ ___   ___ /   \ _ __ | |     | |  | |_) |
 * | |    | '_ ` _ \ / _ \     | '_ \| |     | |  |  _ < 
 * | |____| | | | | |  __/     | | | | |     | |  | |_) |
 * |______|_| |_| |_|\___|\___/|_| |_|_|     |_|  |____/ 
 * 
 * LemonTTB is a Discord Bot designed to assist the GM during tabletop sessions using Discord as communication.
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static final String VERSION = "v0.0.1";
    public static final boolean DEV = true;
    public static final String RESOURCE_PATH = "./data";

    public static File configPath;
    public static File audioPath;
    public static JDA jda;
    public static LemonTTB_AudioManager audioManager;

    public static void main(String[] args) {
        // INIT: Startup
        File logo = new File(Resources.getResource("Logo.txt").getPath());
        try (BufferedReader br = new BufferedReader(new FileReader(logo, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File startup = new File(Resources.getResource("Startup.txt").getPath());
        try (BufferedReader br = new BufferedReader(new FileReader(startup))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // INIT: Logger
        Logger.logFilePath = new File(RESOURCE_PATH, "/logs").getPath();
        Logger.debug = true;
        Logger.trace = true;
        Logger.debugBlacklist = new String[] { ".jda.", ".lava.", ".lavaplayer." };

        // INIT: Config
        configPath = new File(RESOURCE_PATH, DEV ? "/config/botConfig.txt.dev" : "/config/botConfig.txt");
        Config.configFile = configPath;
        Config.load();

        // INIT: Audio
        audioManager = new LemonTTB_AudioManager();
        audioManager.createPlayer();
        audioPath = new File(RESOURCE_PATH, "/music");

        // INIT: JDA
        buildJDA();
    }

    private static void buildJDA() {
        JDABuilder builder = JDABuilder.createDefault(Config.options.token);

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
