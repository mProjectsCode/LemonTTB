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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IOHelper {
    /**
     * Returns the filepath to the first file with the same name as fileName.
     * Searches all subfolders recursevly.
     * 
     * @param fileName the name to match
     * @param folder   the folder to search
     * @return the filepath
     */
    public static File getPathByFileName(String fileName, File folder) {
        fileName = fileName.toLowerCase();
        File[] listOfFiles = folder.listFiles();

        for (int j = 0; j < listOfFiles.length; j++) {
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
     * Returns a list of filepaths containing all the files conatining fileName in
     * its file name.
     * Searches all subfolders recursevly.
     * 
     * @param fileName the name a file needs to contain
     * @param folder   the folder to search
     * @return the list of fielpaths
     */
    public static File[] findPathsByFileName(String fileName, File folder) {
        fileName = fileName.toLowerCase();
        List<File> paths = new ArrayList<File>();
        File[] listOfFiles = folder.listFiles();

        for (int j = 0; j < listOfFiles.length; j++) {
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

        for (int j = 0; j < listOfFiles.length; j++) {
            if (listOfFiles[j].isFile()) {
                if (Objects.equals(listOfFiles[j].getName().toLowerCase(), fileName)) {
                    return listOfFiles[j];
                }
            }
        }

        return null;
    }

    /**
     * Returns a list of filepaths containing all the files conatining fileName in
     * its file name.
     * Searches only the given folder.
     * 
     * @param fileName the name a file needs to contain
     * @param folder   the folder to search
     * @return the list of fielpaths
     */
    public static File[] findPathsByFileNameInFolder(String fileName, File folder) {
        fileName = fileName.toLowerCase();
        List<File> paths = new ArrayList<File>();
        File[] listOfFiles = folder.listFiles();

        for (int j = 0; j < listOfFiles.length; j++) {
            if (listOfFiles[j].isFile()) {
                if (listOfFiles[j].getName().toLowerCase().contains(fileName)) {
                    paths.add(listOfFiles[j]);
                }
            }
        }

        return paths.toArray(new File[0]);
    }
}
