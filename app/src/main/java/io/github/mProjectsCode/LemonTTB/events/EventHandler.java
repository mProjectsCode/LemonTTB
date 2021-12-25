package io.github.mProjectsCode.LemonTTB.events;

import io.github.mProjectsCode.LemonTTB.Logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    private final static Logger LOGGER = Logger.getLogger(EventHandler.class);

    public static List<EventSubscription> eventSubscriptions = new ArrayList<>();

    public static void subscribe(EventType eventType, EventListener eventSubscriber) {
        eventSubscriptions.add(new EventSubscription(eventSubscriber, eventType));
        LOGGER.logDebug(eventSubscriber.getClass().getName() + " subscribed to event of type: " + eventType.name());
    }

    public static void trigger(Event event) {
        for (EventSubscription eventSubscription : eventSubscriptions) {
            eventSubscription.sendEvent(event);
        }
    }
}
