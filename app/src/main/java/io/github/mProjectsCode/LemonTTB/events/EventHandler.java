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

import io.github.mProjectsCode.LemonTTB.Logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Event handler.
 */
public class EventHandler {
    private final static Logger LOGGER = Logger.getLogger(EventHandler.class);

    /**
     * The constant eventSubscriptions.
     */
    public static List<EventSubscription> eventSubscriptions = new ArrayList<>();

    /**
     * Subscribe.
     *
     * @param eventType       the event type
     * @param eventSubscriber the event subscriber
     */
    public static void subscribe(EventType eventType, EventListener eventSubscriber) {
        eventSubscriptions.add(new EventSubscription(eventSubscriber, eventType));
        LOGGER.logDebug(eventSubscriber.getClass().getName() + " subscribed to event of type: " + eventType.name());
    }

    /**
     * Subscribe.
     *
     * @param eventGroup      the event group
     * @param eventSubscriber the event subscriber
     */
    public static void subscribe(EventGroup eventGroup, EventListener eventSubscriber) {
        eventSubscriptions.add(new EventSubscription(eventSubscriber, eventGroup));
        LOGGER.logDebug(eventSubscriber.getClass().getName() + " subscribed to event group of type: " + eventGroup.name());
    }

    /**
     * Trigger.
     *
     * @param event the event
     */
    public static void trigger(Event event) {
        LOGGER.logEvent(event);
        for (EventSubscription eventSubscription : eventSubscriptions) {
            eventSubscription.sendEvent(event);
        }
    }
}
