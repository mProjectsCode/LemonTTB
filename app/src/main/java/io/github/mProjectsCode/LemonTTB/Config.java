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

package io.github.mProjectsCode.LemonTTB;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.exceptions.StartUpException;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Config.
 */
public class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class);

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * The constant options.
     */
    public static Options options;

    /**
     * The constant configFile.
     */
    public static File configFile;

    /**
     * Load.
     *
     * @param file the file
     */
    public static void load(File file) {
        if (!file.exists()) {
            try {
                options = Options.getDefaultOptions();
                write(file);
            } catch (IOException e) {
                throw new RuntimeException("Could not write default configuration file.", e);
            }
        }

        String json = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            json = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file.", e);
        }

        try {
            Options o = gson.fromJson(json, Options.class);
            if (Objects.equals(o.version, App.VERSION)) {
                options = o;
            } else {
                // TODO: For new version build config migration
                options = Options.getDefaultOptions();
            }
        } catch (Exception e) {
            try {
                LOGGER.logWarning("Could not read config. Resetting to default...");
                options = Options.getDefaultOptions();
                write(file);
            } catch (IOException e1) {
                throw new RuntimeException("Could not write default configuration file.", e1);
            }
        }
    }

    /**
     * Write.
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public static void write(File file) throws IOException {
        File dir = file.getParentFile();

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Could not create parent directories");
            }
        } else if (!dir.isDirectory()) {
            throw new IOException("The parent file is not a directory");
        }

        try (Writer writer = new FileWriter(file)) {
            writer.write("# This is the configuration file for LemonTTB.\n");
            writer.write("#\n");
            writer.write("# You can find information on editing this file and all the available options here:\n");
            writer.write("# https://github.com/mProjectsCode/LemonTTB/wiki/Config-File\n");
            writer.write("#\n");
            writer.write(gson.toJson(options));
        }
    }

    /**
     * Save.
     */
    public static void save() {
        try {
            write(configFile);
        } catch (IOException e) {
            LOGGER.logError("Error while trying to save config.");
            LOGGER.logError(e);
        }
    }

    /**
     * Load.
     */
    public static void load() {
        load(configFile);
    }

    /**
     * The type Options.
     */
    public static class Options {
        /**
         * The Version.
         */
        public String version;

        /**
         * The Token.
         */
        public String token;
        /**
         * The Prefix.
         */
        public String prefix;
        /**
         * The Bot owner.
         */
        public String botOwner;
        /**
         * The Status channel.
         */
        public String statusChannel;
        /**
         * The Voice channel main.
         */
        public String primaryVoiceChannel;
        /**
         * The Voice channel secondary.
         */
        public String secondaryVoiceChannel;
        /**
         * The Name Mappings.
         */
        public Map<String, String> nameMappings;
        /**
         * The Default volume.
         */
        public int defaultVolume;

        /**
         * Instantiates a new Options.
         */
        public Options() {
            this.setDefaultValues();
        }

        /**
         * Gets default options.
         *
         * @return the default options
         */
        public static Options getDefaultOptions() {
            return new Options();
        }

        private void setDefaultValues() {
            this.version = App.VERSION;
            this.token = "";
            this.prefix = ".";
            this.botOwner = "";
            this.statusChannel = "";
            this.primaryVoiceChannel = "";
            this.secondaryVoiceChannel = "";
            this.nameMappings = new HashMap<String, String>();
            this.defaultVolume = 10;
        }

        /**
         * Is config filled boolean.
         *
         * @return the boolean
         */
        public boolean isConfigFilled() {
            if (Objects.equals(token, "")) {
                LOGGER.logWarning("token empty");
                return false;
            }
            if (Objects.equals(prefix, "")) {
                LOGGER.logWarning("prefix empty");
                return false;
            }
            if (Objects.equals(botOwner, "")) {
                LOGGER.logWarning("bot owner empty");
                return false;
            }
            if (Objects.equals(statusChannel, "")) {
                LOGGER.logWarning("status channel empty");
                return false;
            }
            if (Objects.equals(primaryVoiceChannel, "")) {
                LOGGER.logWarning("primary channel empty");
                return false;
            }
            if (Objects.equals(secondaryVoiceChannel, "")) {
                LOGGER.logWarning("secondary channel empty");
                return false;
            }
            return true;
        }

        /**
         * Validate config.
         *
         * @throws StartUpException the start up exception
         */
        public void validateConfig() throws StartUpException {
            User botOwner = App.jda.getUserById(this.botOwner);
            if (Objects.equals(botOwner, null)) {
                throw new StartUpException("Bot owner not found. Check the config file.", App.StartUpPhase.CONFIG);
            }

            GuildChannel statusChannel = App.jda.getGuildChannelById(this.statusChannel);
            if (Objects.equals(statusChannel, null)) {
                throw new StartUpException("Status channel not found. Check the config file.", App.StartUpPhase.CONFIG);
            }

            GuildChannel primaryVoiceChannel = App.jda.getGuildChannelById(this.primaryVoiceChannel);
            if (Objects.equals(primaryVoiceChannel, null)) {
                throw new StartUpException("Primary Voice Channel not found. Check the config file.", App.StartUpPhase.CONFIG);
            }

            GuildChannel secondaryVoiceChannel = App.jda.getGuildChannelById(this.secondaryVoiceChannel);
            if (Objects.equals(secondaryVoiceChannel, null)) {
                throw new StartUpException("Secondary Voice Channel not found. Check the config file.", App.StartUpPhase.CONFIG);
            }
        }
    }
}
