/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021-2022
 * Developed by Moritz Jung
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
 *
 */

package io.github.mProjectsCode.LemonTTB;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import io.github.mProjectsCode.LemonTTB.LemonTTB_Audio.LemonTTB_AudioManager;
import io.github.mProjectsCode.LemonTTB.Logger.ConsoleColors;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.commands.CommandHandler;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import io.github.mProjectsCode.LemonTTB.events.payloads.payloads.SuccessPayload;
import io.github.mProjectsCode.LemonTTB.exceptions.StartUpException;
import io.github.mProjectsCode.LemonTTB.moderation.VoiceChannelHandler;
import io.github.mProjectsCode.LemonTTB.nameMappings.NameMappingsHandler;
import io.github.mProjectsCode.LemonTTB.permissions.PermissionHandler;
import io.github.mProjectsCode.LemonTTB.springboot.Application;
import io.github.mProjectsCode.LemonTTB.users.UserHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.boot.SpringApplication;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
    private static final Gson gson = new Gson();
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
     * The constant isBotOnline.
     */
    public static boolean isBotOnline = false;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // set system properties
        System.setProperty("java.util.logging.config.file", Resources.getResource("commons-logging.properties").getPath());
        LOGGER.logInfo(System.getProperty("java.util.logging.config.file"));

        // INIT: Logger
        try {
            initLogger();
        } catch (StartUpException e) {
            exit("Error while starting logger", e);
        }

        // INIT: Config
        configPath = new File(RESOURCE_PATH, DEV ? "/config/botConfig.txt.dev" : "/config/botConfig.txt");
        Config.configFile = configPath;
        Config.load();
        if (!Config.options.isConfigFilled()) {
            App.exit("Some fields in the config are empty.");
        } else {
            try {
                LOGGER.logInfo("Successfully loaded the config from " + configPath.getCanonicalPath());
            } catch (IOException e) {
                exit("", e);
            }
        }

        // INIT: Spring web server
        SpringApplication.run(Application.class, args);

        // INIT: Startup
        initStartup();

        // test logger
        LOGGER.logTrace("static/test");
        LOGGER.logDebug("static/test");
        LOGGER.logInfo("static/test");
        LOGGER.logWarning("static/test");
        LOGGER.logError("static/test");
    }

    /**
     * Start bot.
     *
     * @throws StartUpException the start up exception
     */
    public static void startBot() throws StartUpException {
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_STATUS,
                "STARTING",
                new SuccessPayload(),
                App.class.getName()
        ));

        // INIT: Documentation
        documentationPath = new File(RESOURCE_PATH, "/documentation");
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_START_UP,
                StartUpPhase.DOCUMENTATION.name(),
                new SuccessPayload(),
                App.class.getName()
        ));

        // INIT: Audio
        audioPath = new File(RESOURCE_PATH, "/music");
        audioManager = new LemonTTB_AudioManager();
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_START_UP,
                StartUpPhase.AUDIO_PLAYER.name(),
                new SuccessPayload(),
                App.class.getName()
        ));

        // INIT: Users/Permissions
        userPath = new File(RESOURCE_PATH, "/users");
        userHandler = new UserHandler();
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_START_UP,
                StartUpPhase.USERS.name(),
                new SuccessPayload(),
                App.class.getName()
        ));
        permissionHandler = new PermissionHandler(true);
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_START_UP,
                StartUpPhase.PERMISSIONS.name(),
                new SuccessPayload(),
                App.class.getName()
        ));

        // if(true) {
        //     throw new StartUpException("Log file path invalid.", StartUpPhase.LOGGER);
        // }

        // INIT: Other
        nameMappingsHandler = new NameMappingsHandler();
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_START_UP,
                StartUpPhase.NAME_MAPPINGS.name(),
                new SuccessPayload(),
                App.class.getName()
        ));

        // INIT: JDA
        buildJDA();
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_START_UP,
                StartUpPhase.JDA.name(),
                new SuccessPayload(),
                App.class.getName()
        ));

        // Validate the config file
        Config.options.validateConfig();
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_START_UP,
                StartUpPhase.CONFIG_VALIDATION.name(),
                new SuccessPayload(),
                App.class.getName()
        ));

        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_STATUS,
                "ONLINE",
                new SuccessPayload(),
                App.class.getName()
        ));
    }

    private static void initStartup() {
        InputStream logo = App.class.getClassLoader().getResourceAsStream("Logo.txt");
        assert logo != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(logo))) {
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

    private static void initLogger() throws StartUpException {
        System.out.println(ConsoleColors.WHITE_BRIGHT + "Initializing Logger..." + ConsoleColors.RESET);

        File logFolderPath = new File(RESOURCE_PATH, "/logs");
        try {
            System.out.println("Logfiles can be found here: " + logFolderPath.getCanonicalPath());
        } catch (IOException e) {
            throw new StartUpException("Log file path invalid.", StartUpPhase.LOGGER);
        }
        Logger.setLogFilePath(logFolderPath.getPath());
        Logger.enableDebug(true);
        Logger.enableTrace(false);
        Logger.setDebugBlacklist(new String[]{".jda.", ".lava.", ".lavaplayer.", "DefaultListableBeanFactory", "ConditionEvaluationReportLoggingListener", ".apache.http."});
    }

    private static void buildJDA() throws StartUpException {
        JDABuilder builder = JDABuilder.createDefault(Config.options.token);

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);

        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        builder.setAudioSendFactory(new NativeAudioSendFactory());
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching(Config.options.prefix + "help"));

        builder.addEventListeners(new CommandHandler(), new VoiceChannelHandler());

        try {
            jda = builder.build();
            isBotOnline = true;
        } catch (LoginException e) {
            throw new StartUpException("Could not log in to the discord API.", StartUpPhase.JDA);
        }

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new StartUpException("Interruption while waiting for jda to ready.", StartUpPhase.JDA);
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

    /**
     * Shutdown bot.
     *
     * @param message the message
     */
    public static void shutdownBot(String message) {
        LOGGER.logInfo("Bot is shutting down with message: ");
        LOGGER.logError(message);
        isBotOnline = false;
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.BOT_STATUS,
                "STOP",
                new SuccessPayload(),
                App.class.getName()
        ));
        if (!Objects.equals(jda, null)) {
            jda.shutdown();
        }
    }

    /**
     * The enum Start up phase.
     */
    public enum StartUpPhase {
        /**
         * Config source.
         */
        CONFIG,
        /**
         * Config validation start up phase.
         */
        CONFIG_VALIDATION,
        /**
         * Logger source.
         */
        LOGGER,
        /**
         * Documentation start up phase.
         */
        DOCUMENTATION,
        /**
         * Jda source.
         */
        JDA,
        /**
         * Audio player start up phase.
         */
        AUDIO_PLAYER,
        /**
         * Users source.
         */
        USERS,
        /**
         * Permissions source.
         */
        PERMISSIONS,
        /**
         * Name mappings start up phase.
         */
        NAME_MAPPINGS,
        /**
         * Commands source.
         */
        COMMANDS
    }
}
