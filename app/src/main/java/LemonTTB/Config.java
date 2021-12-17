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

package LemonTTB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import com.google.gson.Gson;

import LemonTTB.Logger.Logger;

/**
 * The type Config.
 */
public class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class);

    private static final Gson gson = new Gson();

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
        public String voiceChannelMain;
        /**
         * The Voice channel secondary.
         */
        public String voiceChannelSecondary;
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

        private void setDefaultValues() {
            this.version = App.VERSION;
            this.token = "";
            this.prefix = ".";
            this.botOwner = "";
            this.statusChannel = "";
            this.voiceChannelMain = "";
            this.voiceChannelSecondary = "";
            this.defaultVolume = 10;
        }

        /**
         * Gets default options.
         *
         * @return the default options
         */
        public static Options getDefaultOptions() {
            return new Options();
        }
    }
}
