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

package io.github.mProjectsCode.LemonTTB.events;

import java.util.Objects;

/**
 * The type Event subscription.
 */
public class EventSubscription {
    private final EventListener eventListener;
    private final EventGroup eventGroup;
    private final EventType eventType;

    /**
     * Instantiates a new Event subscription.
     *
     * @param eventSubscriber the event subscriber
     * @param eventType       the event type
     */
    public EventSubscription(EventListener eventSubscriber, EventType eventType) {
        this.eventListener = eventSubscriber;
        this.eventGroup = null;
        this.eventType = eventType;
    }

    /**
     * Instantiates a new Event subscription.
     *
     * @param eventSubscriber the event subscriber
     * @param eventGroup      the event group
     */
    public EventSubscription(EventListener eventSubscriber, EventGroup eventGroup) {
        this.eventListener = eventSubscriber;
        this.eventGroup = eventGroup;
        this.eventType = null;
    }

    /**
     * Gets event listener.
     *
     * @return the event listener
     */
    public EventListener getEventListener() {
        return eventListener;
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
     * Gets event type.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Send event.
     *
     * @param event the event
     */
    public void sendEvent(Event event) {
        if (Objects.equals(eventGroup, null)) {
            if (Objects.equals(event.getEventType(), eventType)) {
                eventListener.onEvent(event);
            }
        } else {
            if (Objects.equals(event.getEventGroup(), eventGroup)) {
                eventListener.onEvent(event);
            }
        }
    }
}
