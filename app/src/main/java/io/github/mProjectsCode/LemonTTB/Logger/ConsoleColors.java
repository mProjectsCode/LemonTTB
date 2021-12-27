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

package io.github.mProjectsCode.LemonTTB.Logger;

/**
 * The type Console colors.
 */
public class ConsoleColors {
    /**
     * The constant RESET.
     */
    public static final String RESET = "\033[0m"; // Text Reset

    // REGULAR COLORS
    /**
     * The constant BLACK.
     */
    public static final String BLACK = "\033[0;30m"; // BLACK
    /**
     * The constant RED.
     */
    public static final String RED = "\033[0;31m"; // RED
    /**
     * The constant GREEN.
     */
    public static final String GREEN = "\033[0;32m"; // GREEN
    /**
     * The constant YELLOW.
     */
    public static final String YELLOW = "\033[0;33m"; // YELLOW
    /**
     * The constant BLUE.
     */
    public static final String BLUE = "\033[0;34m"; // BLUE
    /**
     * The constant PURPLE.
     */
    public static final String PURPLE = "\033[0;35m"; // PURPLE
    /**
     * The constant CYAN.
     */
    public static final String CYAN = "\033[0;36m"; // CYAN
    /**
     * The constant WHITE.
     */
    public static final String WHITE = "\033[0;37m"; // WHITE

    // BOLD
    /**
     * The constant BLACK_BOLD.
     */
    public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
    /**
     * The constant RED_BOLD.
     */
    public static final String RED_BOLD = "\033[1;31m"; // RED
    /**
     * The constant GREEN_BOLD.
     */
    public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
    /**
     * The constant YELLOW_BOLD.
     */
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    /**
     * The constant BLUE_BOLD.
     */
    public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
    /**
     * The constant PURPLE_BOLD.
     */
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    /**
     * The constant CYAN_BOLD.
     */
    public static final String CYAN_BOLD = "\033[1;36m"; // CYAN
    /**
     * The constant WHITE_BOLD.
     */
    public static final String WHITE_BOLD = "\033[1;37m"; // WHITE

    // UNDERLINE
    /**
     * The constant BLACK_UNDERLINED.
     */
    public static final String BLACK_UNDERLINED = "\033[4;30m"; // BLACK
    /**
     * The constant RED_UNDERLINED.
     */
    public static final String RED_UNDERLINED = "\033[4;31m"; // RED
    /**
     * The constant GREEN_UNDERLINED.
     */
    public static final String GREEN_UNDERLINED = "\033[4;32m"; // GREEN
    /**
     * The constant YELLOW_UNDERLINED.
     */
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    /**
     * The constant BLUE_UNDERLINED.
     */
    public static final String BLUE_UNDERLINED = "\033[4;34m"; // BLUE
    /**
     * The constant PURPLE_UNDERLINED.
     */
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    /**
     * The constant CYAN_UNDERLINED.
     */
    public static final String CYAN_UNDERLINED = "\033[4;36m"; // CYAN
    /**
     * The constant WHITE_UNDERLINED.
     */
    public static final String WHITE_UNDERLINED = "\033[4;37m"; // WHITE

    // BACKGROUND
    /**
     * The constant BLACK_BACKGROUND.
     */
    public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK
    /**
     * The constant RED_BACKGROUND.
     */
    public static final String RED_BACKGROUND = "\033[41m"; // RED
    /**
     * The constant GREEN_BACKGROUND.
     */
    public static final String GREEN_BACKGROUND = "\033[42m"; // GREEN
    /**
     * The constant YELLOW_BACKGROUND.
     */
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    /**
     * The constant BLUE_BACKGROUND.
     */
    public static final String BLUE_BACKGROUND = "\033[44m"; // BLUE
    /**
     * The constant PURPLE_BACKGROUND.
     */
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    /**
     * The constant CYAN_BACKGROUND.
     */
    public static final String CYAN_BACKGROUND = "\033[46m"; // CYAN
    /**
     * The constant WHITE_BACKGROUND.
     */
    public static final String WHITE_BACKGROUND = "\033[47m"; // WHITE

    // HIGH INTENSITY
    /**
     * The constant BLACK_BRIGHT.
     */
    public static final String BLACK_BRIGHT = "\033[0;90m"; // BLACK
    /**
     * The constant RED_BRIGHT.
     */
    public static final String RED_BRIGHT = "\033[0;91m"; // RED
    /**
     * The constant GREEN_BRIGHT.
     */
    public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
    /**
     * The constant YELLOW_BRIGHT.
     */
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    /**
     * The constant BLUE_BRIGHT.
     */
    public static final String BLUE_BRIGHT = "\033[0;94m"; // BLUE
    /**
     * The constant PURPLE_BRIGHT.
     */
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    /**
     * The constant CYAN_BRIGHT.
     */
    public static final String CYAN_BRIGHT = "\033[0;96m"; // CYAN
    /**
     * The constant WHITE_BRIGHT.
     */
    public static final String WHITE_BRIGHT = "\033[0;97m"; // WHITE

    // BOLD HIGH INTENSITY
    /**
     * The constant BLACK_BOLD_BRIGHT.
     */
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    /**
     * The constant RED_BOLD_BRIGHT.
     */
    public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
    /**
     * The constant GREEN_BOLD_BRIGHT.
     */
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    /**
     * The constant YELLOW_BOLD_BRIGHT.
     */
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    /**
     * The constant BLUE_BOLD_BRIGHT.
     */
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE
    /**
     * The constant PURPLE_BOLD_BRIGHT.
     */
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    /**
     * The constant CYAN_BOLD_BRIGHT.
     */
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m"; // CYAN
    /**
     * The constant WHITE_BOLD_BRIGHT.
     */
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // HIGH INTENSITY BACKGROUNDS
    /**
     * The constant BLACK_BACKGROUND_BRIGHT.
     */
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    /**
     * The constant RED_BACKGROUND_BRIGHT.
     */
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    /**
     * The constant GREEN_BACKGROUND_BRIGHT.
     */
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    /**
     * The constant YELLOW_BACKGROUND_BRIGHT.
     */
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    /**
     * The constant BLUE_BACKGROUND_BRIGHT.
     */
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    /**
     * The constant PURPLE_BACKGROUND_BRIGHT.
     */
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    /**
     * The constant CYAN_BACKGROUND_BRIGHT.
     */
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m"; // CYAN
    /**
     * The constant WHITE_BACKGROUND_BRIGHT.
     */
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE
}