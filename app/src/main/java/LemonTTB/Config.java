package LemonTTB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import com.google.gson.Gson;

public class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class);

    private static Gson gson = new Gson();

    public static Options options;

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

    public static class Options {
        public String version;

        public String token;
        public String prefix;
        public String botOwner;
        public String statusChannel;
        public String voiceChannelMain;
        public String voiceChannelSecondary;
        public int defaultVolume;

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

        public static Options getDefaultOptions() {
            return new Options();
        }
    }
}
