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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IOHelper {
    /**
     * Returns the filepath to the first file with the same name as fileName.
     * Searches all sub folders recursively.
     * 
     * @param fileName the name to match
     * @param folder   the folder to search
     * @return the filepath
     */
    public static File getPathByFileName(String fileName, File folder) {
        fileName = fileName.toLowerCase();
        File[] listOfFiles = folder.listFiles();

        for (int j = 0; j < (listOfFiles != null ? listOfFiles.length : 0); j++) {
            if (listOfFiles[j].isFile()) {
                if (Objects.equals(listOfFiles[j].getName().toLowerCase(), fileName)) {
                    return listOfFiles[j];
                }
            } else if (listOfFiles[j].isDirectory()) {
                return getPathByFileName(fileName, listOfFiles[j]);
            }
        }

        return null;
    }

    /**
     * Returns a list of file paths containing all the files containing fileName in
     * its file name.
     * Searches all sub folders recursively.
     * 
     * @param fileName the name a file needs to contain
     * @param folder   the folder to search
     * @return the list of file paths
     */
    public static File[] findPathsByFileName(String fileName, File folder) {
        fileName = fileName.toLowerCase();
        List<File> paths = new ArrayList<File>();
        File[] listOfFiles = folder.listFiles();

        for (int j = 0; j < (listOfFiles != null ? listOfFiles.length : 0); j++) {
            if (listOfFiles[j].isFile()) {
                if (listOfFiles[j].getName().toLowerCase().contains(fileName)) {
                    paths.add(listOfFiles[j]);
                }
            } else if (listOfFiles[j].isDirectory()) {
                getPathByFileName(fileName, listOfFiles[j]);
            }
        }

        return paths.toArray(new File[0]);
    }

    /**
     * Returns the filepath to the first file with the same name as fileName.
     * Searches only the given folder.
     * 
     * @param fileName the name to match
     * @param folder   the folder to search
     * @return the filepath
     */
    public static File getPathByFileNameInFolder(String fileName, File folder) {
        fileName = fileName.toLowerCase();
        File[] listOfFiles = folder.listFiles();

        for (int j = 0; j < (listOfFiles != null ? listOfFiles.length : 0); j++) {
            if (listOfFiles[j].isFile()) {
                if (Objects.equals(listOfFiles[j].getName().toLowerCase(), fileName)) {
                    return listOfFiles[j];
                }
            }
        }

        return null;
    }

    /**
     * Returns a list of file paths containing all the files containing fileName in
     * its file name.
     * Searches only the given folder.
     * 
     * @param fileName the name a file needs to contain
     * @param folder   the folder to search
     * @return the list of file paths
     */
    public static File[] findPathsByFileNameInFolder(String fileName, File folder) {
        fileName = fileName.toLowerCase();
        List<File> paths = new ArrayList<File>();
        File[] listOfFiles = folder.listFiles();

        for (int j = 0; j < (listOfFiles != null ? listOfFiles.length : 0); j++) {
            if (listOfFiles[j].isFile()) {
                if (listOfFiles[j].getName().toLowerCase().contains(fileName)) {
                    paths.add(listOfFiles[j]);
                }
            }
        }

        return paths.toArray(new File[0]);
    }

    /**
     * Write.
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public static void write(File file, String content) throws IOException {
        File dir = file.getParentFile();

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Could not create parent directories");
            }
        } else if (!dir.isDirectory()) {
            throw new IOException("The parent file is not a directory");
        }

        try (Writer writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    /**
     * Load.
     *
     * @param file the file
     */
    public static String load(File file) {
        if (!file.exists()) {
            throw new RuntimeException("File dose not exist.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file.", e);
        }
    }
}
