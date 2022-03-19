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

package io.github.mProjectsCode.LemonTTB.events.payloads.payloads;

import io.github.mProjectsCode.LemonTTB.events.payloads.Payload;
import io.github.mProjectsCode.LemonTTB.events.payloads.PayloadResponse;

/**
 * The type Success payload.
 */
public class SuccessPayload implements Payload {
    private final String data;
    private final PayloadResponse response = PayloadResponse.OK;

    /**
     * Instantiates a new Success payload.
     *
     * @param data the data
     */
    public SuccessPayload(String data) {
        this.data = data;
    }

    /**
     * Instantiates a new Success payload.
     */
    public SuccessPayload() {
        this.data = "";
    }

    @Override
    public String getResponse() {
        return response.name();
    }

    @Override
    public Object getData() {
        return data;
    }
}
