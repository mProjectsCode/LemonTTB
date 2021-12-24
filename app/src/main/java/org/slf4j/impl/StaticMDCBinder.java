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

package org.slf4j.impl;

import io.github.mProjectsCode.LemonTTB.Logger.LemonTTB_MDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 * The type Static mdc binder.
 */
public final class StaticMDCBinder {
    /**
     * The unique instance of this class.
     */
    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

    /**
     * Private constructor to prevent multiple instances.
     */
    private StaticMDCBinder() {

    }

    /**
     * Returns the singleton.
     *
     * @return the singleton instance
     */
    public static StaticMDCBinder getSingleton() {
        return SINGLETON;
    }

    /**
     * Currently this method always returns an instance of {@link StaticMDCBinder}.
     *
     * @return an MDC adapter
     */
    public MDCAdapter getMDCA() {
        return new LemonTTB_MDCAdapter();
    }

    /**
     * Retrieve the adapter class name.
     *
     * @return The adapter class name.
     */
    public String getMDCAdapterClassStr() {
        return LemonTTB_MDCAdapter.class.getName();
    }
}
