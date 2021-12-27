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
 *
 */

package io.github.mProjectsCode.LemonTTB.events;

import io.github.mProjectsCode.LemonTTB.events.payloads.Payload;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * The type Event.
 */
public class Event {
    private final String time;
    private final UUID id;

    private EventGroup eventGroup;
    private EventType eventType;
    private String name;
    private Payload payload;
    private String originClass;

    /**
     * Instantiates a new Event.
     *
     * @param eventGroup  the event group
     * @param eventType   the event type
     * @param name        the name
     * @param payload     the payload
     * @param originClass the origin class
     */
    public Event(EventGroup eventGroup, EventType eventType, String name, Payload payload, String originClass) {
        this.id = UUID.randomUUID();
        this.eventGroup = eventGroup;
        this.eventType = eventType;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        this.time = dateTimeFormatter.format(localDateTime);
        this.name = name;
        this.payload = payload;
        this.originClass = originClass;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets event group.
     *
     * @return the event group
     */
    public EventGroup getEventGroup() {
        return eventGroup;
    }

    /**
     * Sets event group.
     *
     * @param eventGroup the event group
     */
    public void setEventGroup(EventGroup eventGroup) {
        this.eventGroup = eventGroup;
    }

    /**
     * Gets event type.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Sets event type.
     *
     * @param eventType the event type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     * Gets origin class.
     *
     * @return the origin class
     */
    public String getOriginClass() {
        return originClass;
    }

    /**
     * Sets origin class.
     *
     * @param originClass the origin class
     */
    public void setOriginClass(String originClass) {
        this.originClass = originClass;
    }
}
