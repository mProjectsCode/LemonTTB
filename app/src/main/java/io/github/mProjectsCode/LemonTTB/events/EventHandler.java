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
     * Trigger.
     *
     * @param event the event
     */
    public static void trigger(Event event) {
        for (EventSubscription eventSubscription : eventSubscriptions) {
            eventSubscription.sendEvent(event);
        }
    }
}
