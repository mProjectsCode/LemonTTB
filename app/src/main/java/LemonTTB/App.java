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

import LemonTTB.LemonTTB_Audio.LemonTTB_AudioManager;
import LemonTTB.Logger.ConsoleColors;
import LemonTTB.Logger.Logger;
import LemonTTB.commands.CommandHandler;
import LemonTTB.nameMappings.NameMappingsHandler;
import LemonTTB.permissions.PermissionHandler;
import LemonTTB.users.UserHandler;
import com.google.common.io.Resources;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The type App.
 */
/*
 *   _                             _______ _______ ____
 *  | |                    ___/)  |__   __|__   __|  _ \
 *  | |     ___ _ __ ___  /   \ _ __ | |     | |  | |_) |
 *  | |    / _ \ '_ ` _ \|     | '_ \| |     | |  |  _ <
 *  | |___|  __/ | | | | |     | | | | |     | |  | |_) |
 *  |______\___|_| |_| |_|\___/|_| |_|_|     |_|  |____/
 *
 * LemonTTB is a Discord Bot designed to assist the GM during tabletop sessions, that use Discord for communication.
 */
public class App {
    /**
     * The constant VERSION.
     */
    public static final String VERSION = "v0.0.1";
    /**
     * The constant DEV.
     */
    public static final boolean DEV = true;
    /**
     * The constant RESOURCE_PATH.
     */
    public static final String RESOURCE_PATH = "./data";
    private static final Logger LOGGER = Logger.getLogger(App.class);
    /**
     * The constant configPath.
     */
    public static File configPath;
    /**
     * The constant documentationPath.
     */
    public static File documentationPath;
    /**
     * The constant audioPath.
     */
    public static File audioPath;
    /**
     * The constant userPath.
     */
    public static File userPath;
    /**
     * The constant jda.
     */
    public static JDA jda;
    /**
     * The constant audioManager.
     */
    public static LemonTTB_AudioManager audioManager;
    /**
     * The constant nameMappingHandler.
     */
    public static NameMappingsHandler nameMappingsHandler;
    /**
     * The constant permissionHandler.
     */
    public static PermissionHandler permissionHandler;
    /**
     * The constant userHandler.
     */
    public static UserHandler userHandler;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // INIT: Startup
        initStartup();

        // INIT: Logger
        initLogger();

        // INIT: Config
        configPath = new File(RESOURCE_PATH, DEV ? "/config/botConfig.txt.dev" : "/config/botConfig.txt");
        Config.configFile = configPath;
        Config.load();
        if (!Config.options.isConfigFilled()) {
            App.exit("Some fields in the config are empty.");
        }

        // INIT: Documentation
        documentationPath = new File(RESOURCE_PATH, "/documentation");

        // INIT: Audio
        audioPath = new File(RESOURCE_PATH, "/music");
        audioManager = new LemonTTB_AudioManager();
        audioManager.createPlayer();

        // INIT: Users/Permissions
        userPath = new File(RESOURCE_PATH, "/users");
        userHandler = new UserHandler();
        permissionHandler = new PermissionHandler(true);

        // INIT: Other
        nameMappingsHandler = new NameMappingsHandler();

        // INIT: JDA
        buildJDA();

        // Validate the config file
        Config.options.validateConfig();

        LOGGER.logTrace("test");
        LOGGER.logDebug("test");
        LOGGER.logInfo("test");
        LOGGER.logWarning("test");
        LOGGER.logError("test");
    }

    private static void initStartup() {
        File logo = new File(Resources.getResource("Logo.txt").getPath());
        try (BufferedReader br = new BufferedReader(new FileReader(logo, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("LemonTTB is a Discord Bot designed to assist the GM during tabletop sessions, that use Discord for communication.");
        System.out.println();
        System.out.println(ConsoleColors.WHITE_BRIGHT + "The source files can be found here: " + ConsoleColors.RESET);
        System.out.println("https://github.com/mProjectsCode/LemonTTB");
        System.out.println();
        System.out.println(ConsoleColors.WHITE_BRIGHT + "License:" + ConsoleColors.RESET);
        System.out.println("LemonTTB is distributed under the GPL-3.0 License.");
        System.out.println();
    }

    private static void initLogger() {
        System.out.println(ConsoleColors.WHITE_BRIGHT + "Initializing Logger..." + ConsoleColors.RESET);

        File logFolderPath = new File(RESOURCE_PATH, "/logs");
        try {
            System.out.println("Logfiles can be found here: " + logFolderPath.getCanonicalPath());
        } catch (IOException e) {
            exit("Failed to set filepath for logger!", e);
        }
        Logger.setLogFilePath(logFolderPath.getPath());
        Logger.enableDebug(true);
        Logger.enableTrace(true);
        Logger.setDebugBlacklist(new String[]{".jda.", ".lava.", ".lavaplayer."});
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

    /**
     * Exit.
     *
     * @param message the message
     */
    public static void exit(String message) {
        LOGGER.logError("Program is exiting with message: ");
        LOGGER.logError(message);
        System.exit(0);
    }

    /**
     * Exit.
     *
     * @param message the message
     * @param e       the exception
     */
    public static void exit(String message, Exception e) {
        LOGGER.logError("Program is exiting with message: ");
        LOGGER.logError(message);
        LOGGER.logError(e);
        System.exit(0);
    }
}
