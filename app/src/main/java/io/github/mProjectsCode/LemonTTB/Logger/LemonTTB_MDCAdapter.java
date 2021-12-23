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

import org.slf4j.spi.MDCAdapter;

import java.util.Map;

/**
 * TODO: accually implement this stuff
 */
public class LemonTTB_MDCAdapter implements MDCAdapter {

    @Override
    public void put(String key, String val) {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void remove(String key) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        return null;
    }

    @Override
    public void setContextMap(Map<String, String> contextMap) {

    }

}
